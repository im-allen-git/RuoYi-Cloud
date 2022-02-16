package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysCollectionResult;

/**
 * sys_http_collection的执行结果Mapper接口
 * 
 * @author ruoyi
 * @date 2022-02-15
 */
public interface SysCollectionResultMapper 
{
    /**
     * 查询sys_http_collection的执行结果
     * 
     * @param id sys_http_collection的执行结果主键
     * @return sys_http_collection的执行结果
     */
    public SysCollectionResult selectSysCollectionResultById(Long id);

    /**
     * 查询sys_http_collection的执行结果列表
     * 
     * @param sysCollectionResult sys_http_collection的执行结果
     * @return sys_http_collection的执行结果集合
     */
    public List<SysCollectionResult> selectSysCollectionResultList(SysCollectionResult sysCollectionResult);

    /**
     * 新增sys_http_collection的执行结果
     * 
     * @param sysCollectionResult sys_http_collection的执行结果
     * @return 结果
     */
    public int insertSysCollectionResult(SysCollectionResult sysCollectionResult);

    /**
     * 修改sys_http_collection的执行结果
     * 
     * @param sysCollectionResult sys_http_collection的执行结果
     * @return 结果
     */
    public int updateSysCollectionResult(SysCollectionResult sysCollectionResult);

    /**
     * 删除sys_http_collection的执行结果
     * 
     * @param id sys_http_collection的执行结果主键
     * @return 结果
     */
    public int deleteSysCollectionResultById(Long id);

    /**
     * 批量删除sys_http_collection的执行结果
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCollectionResultByIds(Long[] ids);
}
