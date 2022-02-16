package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysHttpCollection;

import java.util.List;

/**
 * http数据收集Service接口
 * 
 * @author ruoyi
 * @date 2022-02-14
 */
public interface ISysHttpCollectionService 
{
    /**
     * 查询http数据收集
     * 
     * @param id http数据收集主键
     * @return http数据收集
     */
    public SysHttpCollection selectSysHttpCollectionById(Long id);

    /**
     * 查询http数据收集列表
     * 
     * @param sysHttpCollection http数据收集
     * @return http数据收集集合
     */
    public List<SysHttpCollection> selectSysHttpCollectionList(SysHttpCollection sysHttpCollection);

    /**
     * 新增http数据收集
     * 
     * @param sysHttpCollection http数据收集
     * @return 结果
     */
    public int insertSysHttpCollection(SysHttpCollection sysHttpCollection);

    /**
     * 修改http数据收集
     * 
     * @param sysHttpCollection http数据收集
     * @return 结果
     */
    public int updateSysHttpCollection(SysHttpCollection sysHttpCollection);

    /**
     * 批量删除http数据收集
     * 
     * @param ids 需要删除的http数据收集主键集合
     * @return 结果
     */
    public int deleteSysHttpCollectionByIds(Long[] ids);

    /**
     * 删除http数据收集信息
     * 
     * @param id http数据收集主键
     * @return 结果
     */
    public int deleteSysHttpCollectionById(Long id);
}
