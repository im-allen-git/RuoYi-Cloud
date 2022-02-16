package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysCollectionResultMapper;
import com.ruoyi.system.domain.SysCollectionResult;
import com.ruoyi.system.service.ISysCollectionResultService;

/**
 * sys_http_collection的执行结果Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-02-15
 */
@Service
public class SysCollectionResultServiceImpl implements ISysCollectionResultService 
{
    @Autowired
    private SysCollectionResultMapper sysCollectionResultMapper;

    /**
     * 查询sys_http_collection的执行结果
     * 
     * @param id sys_http_collection的执行结果主键
     * @return sys_http_collection的执行结果
     */
    @Override
    public SysCollectionResult selectSysCollectionResultById(Long id)
    {
        return sysCollectionResultMapper.selectSysCollectionResultById(id);
    }

    /**
     * 查询sys_http_collection的执行结果列表
     * 
     * @param sysCollectionResult sys_http_collection的执行结果
     * @return sys_http_collection的执行结果
     */
    @Override
    public List<SysCollectionResult> selectSysCollectionResultList(SysCollectionResult sysCollectionResult)
    {
        return sysCollectionResultMapper.selectSysCollectionResultList(sysCollectionResult);
    }

    /**
     * 新增sys_http_collection的执行结果
     * 
     * @param sysCollectionResult sys_http_collection的执行结果
     * @return 结果
     */
    @Override
    public int insertSysCollectionResult(SysCollectionResult sysCollectionResult)
    {
        sysCollectionResult.setCreateTime(DateUtils.getNowDate());
        return sysCollectionResultMapper.insertSysCollectionResult(sysCollectionResult);
    }

    /**
     * 修改sys_http_collection的执行结果
     * 
     * @param sysCollectionResult sys_http_collection的执行结果
     * @return 结果
     */
    @Override
    public int updateSysCollectionResult(SysCollectionResult sysCollectionResult)
    {
        return sysCollectionResultMapper.updateSysCollectionResult(sysCollectionResult);
    }

    /**
     * 批量删除sys_http_collection的执行结果
     * 
     * @param ids 需要删除的sys_http_collection的执行结果主键
     * @return 结果
     */
    @Override
    public int deleteSysCollectionResultByIds(Long[] ids)
    {
        return sysCollectionResultMapper.deleteSysCollectionResultByIds(ids);
    }

    /**
     * 删除sys_http_collection的执行结果信息
     * 
     * @param id sys_http_collection的执行结果主键
     * @return 结果
     */
    @Override
    public int deleteSysCollectionResultById(Long id)
    {
        return sysCollectionResultMapper.deleteSysCollectionResultById(id);
    }
}
