pipeline {
    agent any

    parameters {
        booleanParam(name: 'checkcode', defaultValue: false, description: '是否执行代码检查？')
        // string(name: 'port', defaultValue: '0', description: '对外访问的端口，范围为30000-32767。如果不需要对外提供端口，请输入0')
        choice(name: 'reserveDBData', choices: 'Yes\nNo', description: '是否需要保留之前部署的数据库数据？')
    }

    environment {
        RD_ENV = 'dev'  // 标识开发测试环境，缺省为开发环境：dev
        GROUP_NAME = ''
        SERVICE_NAME = '-map'
        PVC_WORK = ''
        K8S_CLUSTER_NAME = 'kubernetes'
    }

    stages {
        stage('Clean') {
            when {
                environment name: 'checkcode', value: 'false'
            }
            steps {
                script {
                    GROUP_NAME = JOB_NAME.split("/")[0]
                    SERVICE_NAME = GROUP_NAME + SERVICE_NAME

                    // 如果组在Test视图下，则为测试环境：test
                    if (Jenkins.instance.getView('Test').contains(Jenkins.instance.getItem(GROUP_NAME))) {
                        RD_ENV = 'test'
                    }

                    sh "cp k8s/pvc.yml pvc.yml"
                    sh "sed -i s@__PROJECT__@${SERVICE_NAME}@g pvc.yml"

                    withKubeConfig(clusterName: "${K8S_CLUSTER_NAME}",
                            credentialsId: "k8s-${RD_ENV}",
                            serverUrl: "https://${KUBERNETES_SERVICE_HOST}:${KUBERNETES_SERVICE_PORT_HTTPS}") {
                        if (fileExists('k8s.yml')) {
                            sh "kubectl delete -f k8s.yml -n ${RD_ENV} --ignore-not-found"
                        }

                        try {
                            sh "kubectl create -f pvc.yml -n ${RD_ENV}"
                        } catch (ex) {
                        }
                    }
                }
            }
        }
        stage('Check Code') {
            when {
                environment name: 'checkcode', value: 'true'
            }
            steps {
                withMaven(jdk: 'oracle_jdk18', maven: 'maven', mavenSettingsConfig: 'e0af2237-7500-4e99-af21-60cc491267ec', options: [findbugsPublisher(disabled: true)]) {
                    sh 'mvn clean compile checkstyle:checkstyle findbugs:findbugs pmd:pmd test sonar:sonar -P jacoco -Dmaven.test.failure.ignore=true'
                }
                recordIssues(tools: [checkStyle(), findBugs(useRankAsPriority: true), pmdParser()])
            }
        }
        stage('Package') {
            when {
                environment name: 'checkcode', value: 'false'
            }
            steps {
                withMaven(jdk: 'oracle_jdk18', maven: 'maven', mavenSettingsConfig: 'e0af2237-7500-4e99-af21-60cc491267ec', options: [findbugsPublisher(disabled: true)]) {
                    sh 'mvn clean package'
                }
                // stash includes: '**/target/*.jar', name: 'app'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Deploy') {
            when {
                environment name: 'checkcode', value: 'false'
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                sh "rm -rf tmp"
                sh "mkdir -p tmp/config"

                sh "rm -rf tmp_sql"
                sh "mkdir -p  tmp_sql/${JOB_NAME}"

                sh "cp target/*.jar tmp"

                sh "cp k8s/backend.yml k8s.yml"
                sh "cat k8s/backend-service.yml k8s/ingress.yml > k8s-service.yml"
                sh "sed -i s@__PROJECT__@${SERVICE_NAME}@g k8s.yml"
                sh "sed -i s@__PROJECT__@${SERVICE_NAME}@g k8s-service.yml"
                sh "sed -i s@__ENV__@${RD_ENV}@g k8s.yml"
                sh "sed -i s@__GROUP_NAME__@${GROUP_NAME}@g k8s.yml"
                sh "sed -i s@__ARTIFACT_ID__@${readMavenPom().getArtifactId()}@g k8s.yml"
                sh "sed -i s@__VERSION__@${readMavenPom().getVersion()}@g k8s.yml"

                sh "cp sql/create.sql tmp_sql/${JOB_NAME}"
                sh "cp sql/init_schema.sql tmp_sql/${JOB_NAME}"
                sh "cp sql/init_data.sql tmp_sql/${JOB_NAME}"
                sh "sed -i s@\\`map\\`@${GROUP_NAME}_map@g tmp_sql/${JOB_NAME}/*"

                script {
                    datas = readYaml file: "src/main/resources/application-${RD_ENV}.yml"
                    datas.spring.datasource.url = "jdbc:mysql://__ENV__-mysql:3306/__GROUP_NAME___map?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true"
                    datas.spring.datasource.username = "wafer"
                    datas.spring.datasource.password = "wafer"
                    datas.server.port = 8080

                    writeYaml file: "tmp/config/bootstrap.yml", data: datas

                    withKubeConfig(clusterName: "${K8S_CLUSTER_NAME}",
                            credentialsId: "k8s-${RD_ENV}",
                            serverUrl: "https://${KUBERNETES_SERVICE_HOST}:${KUBERNETES_SERVICE_PORT_HTTPS}") {
                        if (params.reserveDBData == 'No') {
                            MYSQL_POD = sh(
                                    script: "kubectl get pod -l app=${RD_ENV},tier=mysql -n ${RD_ENV} --field-selector=status.phase=Running --ignore-not-found -o custom-columns=name:.metadata.name --no-headers=true | head -1",
                                    returnStdout: true
                            ).trim()

                            RET = sh(
                                    script: "kubectl get pvc sql --no-headers=true -o custom-columns=pv:.spec.volumeName -n ${RD_ENV}",
                                    returnStdout: true
                            ).trim()

                            SQL_PATH = "${RD_ENV}-sql-" + RET

                            ftpPublisher failOnError: true,
                                    publishers: [
                                            [configName: 'ftp_ds1819_dev', transfers: [
                                                    [cleanRemote: true,
                                                     remoteDirectory: "${SQL_PATH}",
                                                     sourceFiles    : "tmp_sql/",
                                                     removePrefix   : "tmp_sql"]
                                            ]]
                                    ]

                            sh "kubectl exec ${MYSQL_POD} -n ${RD_ENV} -- mysql -uwafer -pwafer -e 'source /sql/${JOB_NAME}/create.sql'"
                            sh "kubectl exec ${MYSQL_POD} -n ${RD_ENV} -- mysql -uwafer -pwafer -e 'source /sql/${JOB_NAME}/int_schema.sql'"
                            sh "kubectl exec ${MYSQL_POD} -n ${RD_ENV} -- mysql -uwafer -pwafer -e 'source /sql/${JOB_NAME}/init_data.sql'"
                        }
                        RET = sh(
                                script: "kubectl get pvc ${SERVICE_NAME}-work --no-headers=true -o custom-columns=pv:.spec.volumeName -n ${RD_ENV}",
                                returnStdout: true
                        ).trim()
                        PVC_WORK = "${RD_ENV}-${SERVICE_NAME}-work-" + RET

                        ftpPublisher failOnError: true,
                                publishers: [
                                        [configName: 'ftp_ds1819_dev', transfers: [
                                                [cleanRemote    : true,
                                                 remoteDirectory: "${PVC_WORK}",
                                                 sourceFiles    : 'tmp/',
                                                 removePrefix   : 'tmp']
                                        ]]
                                ]

                        sh "kubectl apply -f k8s-service.yml -n ${RD_ENV}"
                        sh "kubectl apply -f k8s.yml -n ${RD_ENV}"
                    }
                }
            sh "rm -rf ${SERVICE_NAME}.zip"
            zip archive: true, dir: './tmp', glob: '', zipFile: "${SERVICE_NAME}.zip"
            archiveArtifacts artifacts: "**/${SERVICE_NAME}.zip", fingerprint: true
            }
        }
    }
}