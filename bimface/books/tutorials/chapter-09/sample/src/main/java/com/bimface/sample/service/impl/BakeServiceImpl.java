package com.bimface.sample.service.impl;

import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sample.service.BakeService;
import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模型烘焙相关的业务功能实现
 * 在需要使用sdk处注入BimfaceClient
 * 调用sdk方法的同时，可以根据需要加入业务代码
 */
@Service
public class BakeServiceImpl implements BakeService {

    /**
     * 注入BimfaceClient
     */
    @Autowired
    private BimfaceClient bimfaceClient;

    @Override
    public TranslateDatabagDerivativeBean bakeTranslate(Long fileId) throws BimfaceException {
        // 发起烘焙，指定单模型的文件ID即可
        TranslateDatabagDerivativeBean translateBakeDatabag = bimfaceClient.createTranslateBakeDatabag(fileId, null, null);
        return translateBakeDatabag;
    }

    @Override
    public IntegrateDatabagDerivativeBean bakeIntegrate(Long integrateId) throws BimfaceException {
        // 发起烘焙，指定集成模型的集成ID即可
        IntegrateDatabagDerivativeBean integrateBakeDatabag = bimfaceClient.createIntegrateBakeDatabag(integrateId, null, null);
        return integrateBakeDatabag;
    }

    @Override
    public List<TranslateDatabagDerivativeBean> getBakeTranslate(Long fileId) throws BimfaceException {
        // 根据单模型的文件ID查询烘焙状态
        List<TranslateDatabagDerivativeBean> translateBakeDatabags = bimfaceClient.getTranslateBakeDatabags(fileId);
        return translateBakeDatabags;
    }

    @Override
    public List<IntegrateDatabagDerivativeBean> getBakeIntegrate(Long integrateId) throws BimfaceException {
        // 根据集成模型的集成ID查询烘焙状态
        List<IntegrateDatabagDerivativeBean> integrateBakeDatabags = bimfaceClient.getIntegrateBakeDatabags(integrateId);
        return integrateBakeDatabags;
    }
}
