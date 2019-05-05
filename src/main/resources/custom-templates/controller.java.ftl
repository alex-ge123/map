package ${package.Controller};

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.entity.${entity};
import com.wafersystems.virsical.map.service.I${entity}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(value = "${table.entityPath}", description = "${table.comment!} 接口")
@AllArgsConstructor
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

  private final ${table.serviceName} ${table.entityPath}Service;

  @ApiOperation(value = "添加${table.comment!}", notes = "添加${table.comment!}")
  @ApiImplicitParam(name = "${table.entityPath}", value = "${table.comment!}对象", required = true, dataType = "${entity}")
  @PostMapping()
  public R add(@RequestBody ${entity} ${table.entityPath}) {
    return ${table.entityPath}.insert() ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改${table.comment!}", notes = "修改${table.comment!}")
  @ApiImplicitParam(name = "${table.entityPath}", value = "${table.comment!}对象", required = true, dataType = "${entity}")
  @PutMapping()
  public R update(@RequestBody ${entity} ${table.entityPath}) {
    return ${table.entityPath}.updateById() ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除${table.comment!}", notes = "删除${table.comment!}")
  @ApiImplicitParam(name = "id", value = "${table.comment!}id", required = true, dataType = "Integer")
  @DeleteMapping("/{id}")
  public R delete(@PathVariable Integer id) {
    return new ${entity}().deleteById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个${table.comment!}", notes = "根据id获取${table.comment!}")
  @ApiImplicitParam(name = "id", value = "${table.comment!}id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R get(@PathVariable Integer id) {
    return new ${entity}().deleteById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取所有${table.comment!}", notes = "获取所有${table.comment!}")
  @GetMapping("/list")
  public R list() {
    return R.ok(${table.entityPath}Service.list(Wrappers.emptyWrapper()));
  }

  @ApiOperation(value = "获取分页${table.comment!}", notes = "获取分页${table.comment!}")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "${table.entityPath}", value = "${table.comment!}对象", dataType = "${entity}")
  })
  @GetMapping("/page")
  public R page(Page page, ${entity} ${table.entityPath}) {
    return R.ok(new ${entity}().selectPage(page, Wrappers.query(${table.entityPath})));
  }

}
</#if>
