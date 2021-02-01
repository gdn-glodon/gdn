package com.bimface.sample.service.impl;

import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sample.service.IntegrateService;
import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模型集成相关的业务功能实现
 * 在需要使用sdk处注入BimfaceClient
 * 调用sdk方法的同时，可以根据需要加入业务代码
 */
@Service
public class IntegrateServiceImpl implements IntegrateService {

    /**
     * 注入BimfaceClient
     */
    @Autowired
    private BimfaceClient bimfaceClient;

    @Override
    public FileIntegrateBean integrate(FileIntegrateRequest integrateRequest) throws BimfaceException {
        // 发起模型集成，直接传入前端传递的FileIntegrateRequest
        // 接口调用成功后，可以从返回结果中获取integrateId，用于调用模型集成的其他功能，请妥善保存
        // 开发人员可以进行二次开发，将integrateId记录到数据库中
        FileIntegrateBean integrate = bimfaceClient.integrate(integrateRequest);
        return integrate;
    }

    @Override
    public FileIntegrateBean getIntegrate(Long integrateId) throws BimfaceException {
        // 根据integrateId查询模型集成状态
        FileIntegrateBean integrate = bimfaceClient.getIntegrate(integrateId);
        return integrate;
    }

    @Override
    public String getViewToken(Long integrateId) throws BimfaceException {
        // 根据integrateId获取集成模型的View Token
        String viewToken = bimfaceClient.getViewTokenByIntegrateId(integrateId);
        return viewToken;
    }
}
