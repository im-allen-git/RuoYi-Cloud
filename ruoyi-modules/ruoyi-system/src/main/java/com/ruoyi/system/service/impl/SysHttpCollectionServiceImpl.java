package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.domain.SysHttpCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysHttpCollectionMapper;
import com.ruoyi.system.service.ISysHttpCollectionService;

/**
 * http数据收集Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-02-14
 */
@Service
public class SysHttpCollectionServiceImpl implements ISysHttpCollectionService 
{
    @Autowired
    private SysHttpCollectionMapper sysHttpCollectionMapper;

    /**
     * 查询http数据收集
     * 
     * @param id http数据收集主键
     * @return http数据收集
     */
    @Override
    public SysHttpCollection selectSysHttpCollectionById(Long id)
    {
        return sysHttpCollectionMapper.selectSysHttpCollectionById(id);
    }

    /**
     * 查询http数据收集列表
     * 
     * @param sysHttpCollection http数据收集
     * @return http数据收集
     */
    @Override
    public List<SysHttpCollection> selectSysHttpCollectionList(SysHttpCollection sysHttpCollection)
    {
        return sysHttpCollectionMapper.selectSysHttpCollectionList(sysHttpCollection);
    }

    /**
     * 新增http数据收集
     * 
     * @param sysHttpCollection http数据收集
     * @return 结果
     */
    @Override
    public int insertSysHttpCollection(SysHttpCollection sysHttpCollection)
    {
        sysHttpCollection.setCreateTime(DateUtils.getNowDate());
        return sysHttpCollectionMapper.insertSysHttpCollection(sysHttpCollection);
    }

    /**
     * 修改http数据收集
     * 
     * @param sysHttpCollection http数据收集
     * @return 结果
     */
    @Override
    public int updateSysHttpCollection(SysHttpCollection sysHttpCollection)
    {
        sysHttpCollection.setUpdateTime(DateUtils.getNowDate());
        return sysHttpCollectionMapper.updateSysHttpCollection(sysHttpCollection);
    }

    /**
     * 批量删除http数据收集
     * 
     * @param ids 需要删除的http数据收集主键
     * @return 结果
     */
    @Override
    public int deleteSysHttpCollectionByIds(Long[] ids)
    {
        return sysHttpCollectionMapper.deleteSysHttpCollectionByIds(ids);
    }

    /**
     * 删除http数据收集信息
     * 
     * @param id http数据收集主键
     * @return 结果
     */
    @Override
    public int deleteSysHttpCollectionById(Long id)
    {
        return sysHttpCollectionMapper.deleteSysHttpCollectionById(id);
    }



    public void execHttpCollection(SysHttpCollection sysHttpCollection){
        //执行收集数据步骤
        // 1.判断发送方式GET还是POST
        // 2.判断是否HTTPS的访问
        // 3.写入配置的参数
        // 4.获取执行结果
        // 5.确认是否过滤集合结果
        // 6.保存执行结果集合
        // 7.返回直接结果
    }

}
