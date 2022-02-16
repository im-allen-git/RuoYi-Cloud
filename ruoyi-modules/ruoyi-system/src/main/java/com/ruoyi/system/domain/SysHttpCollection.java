package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * http数据收集对象 sys_http_collection
 * 
 * @author jiangxianwei
 * @date 2022-02-15
 */
public class SysHttpCollection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 执行url */
    @Excel(name = "执行url")
    private String httpUrl;

    /** http执行方式: 0默认GET，1 POST */
    @Excel(name = "http执行方式: 0默认GET，1 POST")
    private Integer httpMethod;

    /** 灵活参数配置 */
    @Excel(name = "灵活参数配置")
    private String httpParam;

    /** 数据裁剪 */
    @Excel(name = "数据裁剪")
    private String dataDeal;

    /** 是否启动定时任务 0不启动 1 启动 */
    @Excel(name = "是否启动定时任务 0不启动 1 启动")
    private Integer cronFlag;

    /** 定时任务配置 */
    @Excel(name = "定时任务配置")
    private String cronParam;

    /** 是否支持https,0默认不支持 1支持 */
    @Excel(name = "是否支持https,0默认不支持 1支持")
    private Integer httpsFlag;

    /** https证书 */
    @Excel(name = "https证书")
    private String httpsCertificate;

    /** 代理支持 0默认不支持 1支持 */
    @Excel(name = "代理支持 0默认不支持 1支持")
    private Integer proxyFlag;

    /** 代理参数 */
    @Excel(name = "代理参数")
    private String proxyParam;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long adminId;

    /** 备用1 */
    @Excel(name = "备用1")
    private String rm1;

    /** 备用2 */
    @Excel(name = "备用2")
    private String rm2;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHttpUrl(String httpUrl) 
    {
        this.httpUrl = httpUrl;
    }

    public String getHttpUrl() 
    {
        return httpUrl;
    }
    public void setHttpMethod(Integer httpMethod) 
    {
        this.httpMethod = httpMethod;
    }

    public Integer getHttpMethod() 
    {
        return httpMethod;
    }
    public void setHttpParam(String httpParam) 
    {
        this.httpParam = httpParam;
    }

    public String getHttpParam() 
    {
        return httpParam;
    }
    public void setDataDeal(String dataDeal) 
    {
        this.dataDeal = dataDeal;
    }

    public String getDataDeal() 
    {
        return dataDeal;
    }
    public void setCronFlag(Integer cronFlag) 
    {
        this.cronFlag = cronFlag;
    }

    public Integer getCronFlag() 
    {
        return cronFlag;
    }
    public void setCronParam(String cronParam) 
    {
        this.cronParam = cronParam;
    }

    public String getCronParam() 
    {
        return cronParam;
    }
    public void setHttpsFlag(Integer httpsFlag) 
    {
        this.httpsFlag = httpsFlag;
    }

    public Integer getHttpsFlag() 
    {
        return httpsFlag;
    }
    public void setHttpsCertificate(String httpsCertificate) 
    {
        this.httpsCertificate = httpsCertificate;
    }

    public String getHttpsCertificate() 
    {
        return httpsCertificate;
    }
    public void setProxyFlag(Integer proxyFlag) 
    {
        this.proxyFlag = proxyFlag;
    }

    public Integer getProxyFlag() 
    {
        return proxyFlag;
    }
    public void setProxyParam(String proxyParam) 
    {
        this.proxyParam = proxyParam;
    }

    public String getProxyParam() 
    {
        return proxyParam;
    }
    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }
    public void setRm1(String rm1) 
    {
        this.rm1 = rm1;
    }

    public String getRm1() 
    {
        return rm1;
    }
    public void setRm2(String rm2) 
    {
        this.rm2 = rm2;
    }

    public String getRm2() 
    {
        return rm2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("httpUrl", getHttpUrl())
            .append("httpMethod", getHttpMethod())
            .append("httpParam", getHttpParam())
            .append("dataDeal", getDataDeal())
            .append("cronFlag", getCronFlag())
            .append("cronParam", getCronParam())
            .append("httpsFlag", getHttpsFlag())
            .append("httpsCertificate", getHttpsCertificate())
            .append("proxyFlag", getProxyFlag())
            .append("proxyParam", getProxyParam())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("adminId", getAdminId())
            .append("remark", getRemark())
            .append("rm1", getRm1())
            .append("rm2", getRm2())
            .toString();
    }
}
