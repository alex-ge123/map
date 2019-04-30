#### 地图服务
> 公共模块地图服务，提供地图管理，以组件的方式为其它产品提供地图显示、地图元素事件处理、地图资源状态显示功能。


##### 功能清单

编号	模块	功能
1.	园区管理	　添加、修改、删除、查询
2.	楼宇管理	　添加、修改、删除、查询
3.	楼层管理	　添加、修改、删除、查询
4.	素材类型管理	　添加、修改、删除、查询
5.	素材显示图管理	　添加、修改、删除、查询
6.	素材管理	　添加、修改、删除、查询、开启
7.	地图管理	　
地图基本信息添加，地图底图、缩略图上传；
绘制地图元素路径（单个绘制，多选绘制）；
8.	地图编辑器	
- 地图编辑器面板样式布局；
- 绘制地图底图及地图素材；
- 地图素材面板按素材分类分组；
- 选择素材可拖动到地图上；
- 编辑器功能：
	- 切换多选
	- 复制
		单选，向上下左右紧邻增加多少个元素
		多选，复制组
	- 旋转
		设置旋转度数
	- 间隔
		多选，设置间隔像素
	- 对齐
		左、右、顶部、底部
	- undo/redo
	- 删除
	- 保存
9.	地图展示	　
- 前端封装地图，以组件形式嵌入产品中；
- 支持地图元素选中事件回调功能；
- 事件回调后，业务服务调用地图服务通知；