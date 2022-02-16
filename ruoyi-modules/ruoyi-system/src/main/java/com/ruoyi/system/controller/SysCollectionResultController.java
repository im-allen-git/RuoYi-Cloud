package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.domain.SysCollectionResult;
import com.ruoyi.system.service.ISysCollectionResultService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * sys_http_collection的执行结果Controller
 * 
 * @author ruoyi
 * @date 2022-02-15
 */
@RestController
@RequestMapping("/result")
public class SysCollectionResultController extends BaseController
{
    @Autowired
    private ISysCollectionResultService sysCollectionResultService;

    /**
     * 查询sys_http_collection的执行结果列表
     */
    @RequiresPermissions("system:result:list")
    @GetMapping("/list")
    public TableDataInfo list(SysCollectionResult sysCollectionResult)
    {
        startPage();
        List<SysCollectionResult> list = sysCollectionResultService.selectSysCollectionResultList(sysCollectionResult);
        return getDataTable(list);
    }

    /**
     * 导出sys_http_collection的执行结果列表
     */
    @RequiresPermissions("system:result:export")
    @Log(title = "sys_http_collection的执行结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCollectionResult sysCollectionResult)
    {
        List<SysCollectionResult> list = sysCollectionResultService.selectSysCollectionResultList(sysCollectionResult);
        ExcelUtil<SysCollectionResult> util = new ExcelUtil<SysCollectionResult>(SysCollectionResult.class);
        util.exportExcel(response, list, "sys_http_collection的执行结果数据");
    }

    /**
     * 获取sys_http_collection的执行结果详细信息
     */
    @RequiresPermissions("system:result:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCollectionResultService.selectSysCollectionResultById(id));
    }

    /**
     * 新增sys_http_collection的执行结果
     */
    @RequiresPermissions("system:result:add")
    @Log(title = "sys_http_collection的执行结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCollectionResult sysCollectionResult)
    {
        return toAjax(sysCollectionResultService.insertSysCollectionResult(sysCollectionResult));
    }

    /**
     * 修改sys_http_collection的执行结果
     */
    @RequiresPermissions("system:result:edit")
    @Log(title = "sys_http_collection的执行结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCollectionResult sysCollectionResult)
    {
        return toAjax(sysCollectionResultService.updateSysCollectionResult(sysCollectionResult));
    }

    /**
     * 删除sys_http_collection的执行结果
     */
    @RequiresPermissions("system:result:remove")
    @Log(title = "sys_http_collection的执行结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCollectionResultService.deleteSysCollectionResultByIds(ids));
    }
}
