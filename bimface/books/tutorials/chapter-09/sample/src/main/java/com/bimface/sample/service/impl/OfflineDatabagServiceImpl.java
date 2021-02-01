package com.bimface.sample.service.impl;

import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sample.service.OfflineDatabagService;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 离线数据包相关的业务功能实现
 * 在需要使用sdk处注入BimfaceClient
 * 调用sdk方法的同时，可以根据需要加入业务代码
 */
@Service
public class OfflineDatabagServiceImpl implements OfflineDatabagService {

    /**
     * 注入BimfaceClient
     */
    @Autowired
    private BimfaceClient bimfaceClient;

    @Override
    public DatabagDerivativeBean generateTranslateOfflineDatabag(Long fileId) throws BimfaceException {
        // 初始化生成离线数据包的请求体
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        // 设置fileId，表示生成单模型的离线数据包
        request.setFileId(fileId);
        DatabagDerivativeBean offlineDatabagBean = bimfaceClient.generateOfflineDatabag(request);
        return offlineDatabagBean;
    }

    @Override
    public DatabagDerivativeBean generateIntegrateOfflineDatabag(Long integrateId) throws BimfaceException {
        // 初始化生成离线数据包的请求体
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        // 设置integrateId，表示生成集成模型的离线数据包
        request.setIntegrateId(integrateId);
        DatabagDerivativeBean offlineDatabagBean = bimfaceClient.generateOfflineDatabag(request);
        return offlineDatabagBean;
    }

    @Override
    public DatabagDerivativeBean generateCompareOfflineDatabag(Long compareId) throws BimfaceException {
        // 初始化生成离线数据包的请求体
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        // 设置compareId，表示生成模型对比的离线数据包
        request.setCompareId(compareId);
        DatabagDerivativeBean offlineDatabagBean = bimfaceClient.generateOfflineDatabag(request);
        return offlineDatabagBean;
    }

    @Override
    public List<? extends DatabagDerivativeBean> queryTranslateOfflineDatabag(Long fileId) throws BimfaceException {
        // 根据fileId查询单模型的离线数据包生成情况
        List<? extends DatabagDerivativeBean> databagDerivativeBeans = bimfaceClient.queryTranslateOfflineDatabag(fileId);
        return databagDerivativeBeans;
    }

    @Override
    public List<? extends DatabagDerivativeBean> queryIntegrateOfflineDatabag(Long integrateId) throws BimfaceException {
        // 根据integrateId查询集成模型的离线数据包生成情况
        List<? extends DatabagDerivativeBean> databagDerivativeBeans = bimfaceClient.queryIntegrateOfflineDatabag(integrateId);
        return databagDerivativeBeans;
    }

    @Override
    public List<? extends DatabagDerivativeBean> queryCompareOfflineDatabag(Long compareId) throws BimfaceException {
        // 根据compareid查询模型对比的离线数据包生成情况
        List<? extends DatabagDerivativeBean> databagDerivativeBeans = bimfaceClient.queryCompareOfflineDatabag(compareId);
        return databagDerivativeBeans;
    }

    @Override
    public String getTranslateOfflineDatabagUrl(Long fileId) throws BimfaceException {
        // 根据fileId获取单模型离线数据包的下载地址
        // getOfflineDatabagUrl同时支持单模型/集成模型/模型对比的离线数据包生成，必须指定fileId/integrateId/compareId中的一个
        // databagVersion为数据包版本。如果当前模型只有一个离线数据包，则返回该离线数据包的下载地址；如果有多个，则必须指定数据包版本
        // 当前模型的离线数据包版本可以通过queryTranslateOfflineDatabag方法获取
        String offlineDatabagUrl = bimfaceClient.getOfflineDatabagUrl(fileId, null, null, null);
        return offlineDatabagUrl;
    }

    @Override
    public String getIntegrateOfflineDatabagUrl(Long integrateId) throws BimfaceException {
        // 根据integrateId获取集成模型离线数据包的下载地址
        // getOfflineDatabagUrl同时支持单模型/集成模型/模型对比的离线数据包生成，必须指定fileId/integrateId/compareId中的一个
        // databagVersion为数据包版本。如果当前模型只有一个离线数据包，则返回该离线数据包的下载地址；如果有多个，则必须指定数据包版本
        // 当前模型的离线数据包版本可以通过queryIntegrateOfflineDatabag方法获取
        String offlineDatabagUrl = bimfaceClient.getOfflineDatabagUrl(null, integrateId, null, null);
        return offlineDatabagUrl;
    }

    @Override
    public String getCompareOfflineDatabagUrl(Long compareId) throws BimfaceException {
        // 根据compareId获取模型对比离线数据包的下载地址
        // getOfflineDatabagUrl同时支持单模型/集成模型/模型对比的离线数据包生成，必须指定fileId/integrateId/compareId中的一个
        // databagVersion为数据包版本。如果当前模型只有一个离线数据包，则返回该离线数据包的下载地址；如果有多个，则必须指定数据包版本
        // 当前模型的离线数据包版本可以通过queryCompareOfflineDatabag方法获取
        String offlineDatabagUrl = bimfaceClient.getOfflineDatabagUrl(null, null, compareId, null);
        return offlineDatabagUrl;
    }
}
