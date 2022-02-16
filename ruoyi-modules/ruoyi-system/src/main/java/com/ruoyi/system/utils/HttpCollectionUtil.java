package com.ruoyi.system.utils;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.SysCollectionResult;
import com.ruoyi.system.domain.SysHttpCollection;
import com.ruoyi.system.mapper.SysCollectionResultMapper;
import com.ruoyi.system.mapper.SysHttpCollectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiangxianwei
 * @version 1.0
 * @description: 数据收集工具类
 * @date 2022/2/15 14:20
 */
@Service
public class HttpCollectionUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpCollectionUtil.class);

    @Autowired
    private SysHttpCollectionMapper sysHttpCollectionMapper;
    @Autowired
    private SysCollectionResultMapper sysCollectionResultMapper;
    private OkHttpUtil okHttpUtil = new OkHttpUtil();

    public AjaxResult execCollection(Long collectionId) {

        try {

            SysHttpCollection colBean = sysHttpCollectionMapper.selectSysHttpCollectionById(collectionId);
            if (null != colBean) {
                // 1.判断是否需要https
                JSONObject jsonObject;
                if (colBean.getHttpsFlag() == 0) {
                    // 2.判断http调用方式
                    if (colBean.getHttpMethod() == 0) {
                        jsonObject = okHttpUtil.get(colBean.getHttpUrl());
                    } else {
                        jsonObject = okHttpUtil.post(colBean.getHttpUrl(), JSONObject.parseObject(colBean.getHttpParam()));
                    }
                } else {
                    // 2.判断http调用方式
                    if (colBean.getHttpMethod() == 0) {
                        jsonObject = okHttpUtil.sslGet(colBean.getHttpUrl(), colBean.getHttpsCertificate());
                    } else {
                        jsonObject = okHttpUtil.sslPost(colBean.getHttpUrl(), colBean.getHttpsCertificate(), JSONObject.parseObject(colBean.getHttpParam()));
                    }
                }
                // 3.判断是否需要代理
                // 4.获取执行结果
                // 5.过滤结果集合
                // 6.存储到数据库
                SysCollectionResult collectionRs = new SysCollectionResult();
                collectionRs.setCollectionId(collectionId);
                collectionRs.setResult(null == jsonObject ? null : jsonObject.toJSONString());
                collectionRs.setFilterResult(null == jsonObject ? null : jsonObject.toJSONString());
                sysCollectionResultMapper.insertSysCollectionResult(collectionRs);
                return AjaxResult.success(collectionRs.getId());
            }
            return AjaxResult.error("当前ID不存在");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("execCollection,id:[{}],error:", collectionId, e);
            return AjaxResult.error(e.getMessage());
        }


    }
}
