package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * sys_http_collection的执行结果对象 sys_collection_result
 * 
 * @author ruoyi
 * @date 2022-02-15
 */
public class SysCollectionResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** sys_http_collection表ID */
    @Excel(name = "sys_http_collection表ID")
    private Long collectionId;

    /** 执行结果集合 */
    @Excel(name = "执行结果集合")
    private String result;

    /** 过滤结果集合 */
    @Excel(name = "过滤结果集合")
    private String filterResult;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCollectionId(Long collectionId) 
    {
        this.collectionId = collectionId;
    }

    public Long getCollectionId() 
    {
        return collectionId;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setFilterResult(String filterResult) 
    {
        this.filterResult = filterResult;
    }

    public String getFilterResult() 
    {
        return filterResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("collectionId", getCollectionId())
            .append("result", getResult())
            .append("filterResult", getFilterResult())
            .append("createTime", getCreateTime())
            .toString();
    }
}
