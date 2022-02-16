package com.ruoyi.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.domain.SysHttpCollection;
import com.ruoyi.system.service.ISysHttpCollectionService;
import com.ruoyi.system.task.CronTaskRegistrar;
import com.ruoyi.system.task.SchedulingRunnable;
import com.ruoyi.system.utils.HttpCollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * http数据收集Controller
 *
 * @author jiangxianwei
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/collection")
public class SysHttpCollectionController extends BaseController {
    @Autowired
    private ISysHttpCollectionService sysHttpCollectionService;

    @Autowired
    private HttpCollectionUtil httpCollectionUtil;

    @Autowired
    private CronTaskRegistrar taskRegistrar;

    /**
     * 查询http数据收集列表
     */
    @RequiresPermissions("system:collection:list")
    @GetMapping("/list")
    public TableDataInfo list(SysHttpCollection sysHttpCollection) {
        startPage();
        List<SysHttpCollection> list = sysHttpCollectionService.selectSysHttpCollectionList(sysHttpCollection);
        return getDataTable(list);
    }

    /**
     * 导出http数据收集列表
     */
    @RequiresPermissions("system:collection:export")
    @Log(title = "http数据收集", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysHttpCollection sysHttpCollection) {
        List<SysHttpCollection> list = sysHttpCollectionService.selectSysHttpCollectionList(sysHttpCollection);
        ExcelUtil<SysHttpCollection> util = new ExcelUtil<SysHttpCollection>(SysHttpCollection.class);
        util.exportExcel(response, list, "http数据收集数据");
    }

    /**
     * 获取http数据收集详细信息
     */
    @RequiresPermissions("system:collection:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysHttpCollectionService.selectSysHttpCollectionById(id));
    }

    /**
     * 新增http数据收集
     */
    @RequiresPermissions("system:collection:add")
    @Log(title = "http数据收集", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysHttpCollection sysHttpCollection) {
        Long userId = SecurityUtils.getUserId();
        sysHttpCollection.setAdminId(userId);

        AjaxResult ajaxResult = validateParameter(sysHttpCollection);
        if (null == ajaxResult.get("code") || 500 == (int) ajaxResult.get("code")) {
            return ajaxResult;
        }
        int i = sysHttpCollectionService.insertSysHttpCollection(sysHttpCollection);
        if (i > 0) {
            dealTask(sysHttpCollection.getId());
        }
        return toAjax(i);
    }


    private AjaxResult validateParameter(SysHttpCollection sysHttpCollection) {

        // 判断url是否存在
        Assert.isTrue(StringUtils.isNotEmpty(sysHttpCollection.getHttpUrl()), "URL是空的");
        Assert.isTrue(sysHttpCollection.getHttpUrl().startsWith("http://")
                || sysHttpCollection.getHttpUrl().startsWith("https://"), "URL必须是http或者https开头的");
        // 判断参数配置是否是json数据格式
        if (sysHttpCollection.getHttpMethod() == 1 && StringUtils.isNotEmpty(sysHttpCollection.getHttpParam())) {
            JSONObject jsonObject = JSONObject.parseObject(sysHttpCollection.getHttpParam());
            if (null == jsonObject || jsonObject.size() == 0) {
                return AjaxResult.error("输入的参数配置非json格式");
            }
        }
        // 判断数据裁剪是否是json数据格式
        if (StringUtils.isNotEmpty(sysHttpCollection.getDataDeal()) && !"{}".equalsIgnoreCase(sysHttpCollection.getDataDeal().trim())) {
            JSONObject jsonObject = JSONObject.parseObject(sysHttpCollection.getDataDeal());
            if (null == jsonObject || jsonObject.size() == 0) {
                return AjaxResult.error("输入的数据裁剪非json格式");
            }
        }
        // 如果是支持https的，验证证书是否存在
        if (sysHttpCollection.getHttpsFlag() == 1) {
            if (!FileUtils.checkIsFile(sysHttpCollection.getHttpsCertificate())) {
                return AjaxResult.error("https证书的位置未检测到");
            }
            if (sysHttpCollection.getHttpsCertificate().endsWith(".cer")) {
                return AjaxResult.error("https证书文件名称异常,必须以.cer结尾");
            }
        }
        // 如果cron，则验证cronParam的正确性
        if (sysHttpCollection.getCronFlag() == 1) {
            if (StringUtils.isEmpty(sysHttpCollection.getCronParam())) {
                return AjaxResult.error("cron的参数空");
            }
            boolean validExpression = CronExpression.isValidExpression(sysHttpCollection.getCronParam());
            if (!validExpression) {
                return AjaxResult.error("cron参数验证不通过");
            }
        }
        // 如果是支持代理的，则给出对应的json数据
        /**
         * {useName:xx,passWord:xx,hostName:xx,port:xx}
         */
        if (sysHttpCollection.getProxyFlag() == 1) {
            if (StringUtils.isEmpty(sysHttpCollection.getProxyParam())) {
                return AjaxResult.error("代理参数null");
            }
            JSONObject jsonObject = JSONObject.parseObject(sysHttpCollection.getProxyParam());
            if (jsonObject == null) {
                return AjaxResult.error("代理参数null");
            }
            if (jsonObject.size() < 5) {
                return AjaxResult.error("代理参数需要的字段未填写");
            }
            if (!jsonObject.containsKey("useName") || StringUtils.isEmpty(jsonObject.getString("useName"))) {
                return AjaxResult.error("代理参数useName空的");
            }
            if (!jsonObject.containsKey("passWord") || StringUtils.isEmpty(jsonObject.getString("passWord"))) {
                return AjaxResult.error("代理参数passWord空的");
            }
            if (!jsonObject.containsKey("hostName") || StringUtils.isEmpty(jsonObject.getString("hostName"))) {
                return AjaxResult.error("代理参数hostName空的");
            }
            if (!jsonObject.containsKey("port") || StringUtils.isEmpty(jsonObject.getString("port"))) {
                return AjaxResult.error("代理参数port空的");
            }
        }
        return AjaxResult.success();
    }

    /**
     * 修改http数据收集
     */
    @RequiresPermissions("system:collection:edit")
    @Log(title = "http数据收集", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysHttpCollection sysHttpCollection) {
        AjaxResult ajaxResult = validateParameter(sysHttpCollection);
        if (null == ajaxResult.get("code") || 500 == (int) ajaxResult.get("code")) {
            return ajaxResult;
        }

        int i = sysHttpCollectionService.updateSysHttpCollection(sysHttpCollection);
        dealTask(sysHttpCollection.getId());
        return toAjax(i);
    }

    /**
     * 删除http数据收集
     */
    @RequiresPermissions("system:collection:remove")
    @Log(title = "http数据收集", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        dealTask(ids);
        return toAjax(sysHttpCollectionService.deleteSysHttpCollectionByIds(ids));
    }


    public List<SysHttpCollection> getColList() {
        SysHttpCollection sysHttpCollection = new SysHttpCollection();
        return sysHttpCollectionService.selectSysHttpCollectionList(sysHttpCollection);
    }

    /**
     * 新增和编辑的时候 开启或者关闭定时任务
     *
     * @param id
     */
    private void dealTask(Long id) {
        SysHttpCollection cron = sysHttpCollectionService.selectSysHttpCollectionById(id);
        if (cron.getCronFlag() == 1) {
            SchedulingRunnable task = new SchedulingRunnable(cron, httpCollectionUtil);
            taskRegistrar.removeCronTask(cron.getId());
            taskRegistrar.addCronTask(cron.getId(), task, cron.getCronParam());
        }
        if (cron.getCronFlag() == 0) {
            taskRegistrar.removeCronTask(cron.getId());
        }
    }

    /**
     * 移除定时任务
     *
     * @param ids
     */
    private void dealTask(Long[] ids) {
        if (null != ids && ids.length > 0) {
            for (Long id : ids) {
                taskRegistrar.removeCronTask(id);
            }
        }
    }


    /**
     * 根据参数键名查询参数值
     */
    @RequiresPermissions("system:collection:exec")
    @Log(title = "http数据收集,execCollection:", businessType = BusinessType.OTHER)
    @GetMapping(value = "/exec/{id}")
    public AjaxResult execCollection(@PathVariable Long id) {
        return httpCollectionUtil.execCollection(id);
    }

}
