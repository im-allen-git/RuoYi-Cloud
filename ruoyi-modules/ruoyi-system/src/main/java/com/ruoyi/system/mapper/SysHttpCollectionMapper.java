package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysHttpCollection;

import java.util.List;

/**
 * http数据收集Mapper接口
 * 
 * @author ruoyi
 * @date 2022-02-14
 */
public interface SysHttpCollectionMapper 
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
     * 删除http数据收集
     * 
     * @param id http数据收集主键
     * @return 结果
     */
    public int deleteSysHttpCollectionById(Long id);

    /**
     * 批量删除http数据收集
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysHttpCollectionByIds(Long[] ids);
}
