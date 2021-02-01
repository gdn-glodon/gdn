package com.bimface.sample.service;

import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.exception.BimfaceException;

/**
 * 模型集成相关的业务功能实现
 */
public interface IntegrateService {
    /**
     * 发起模型集成
     *
     * @param integrateRequest {@link FileIntegrateRequest} 发起模型集成的参数
     * @return {@link FileIntegrateBean} 模型集成状态信息
     * @throws BimfaceException
     */
    FileIntegrateBean integrate(FileIntegrateRequest integrateRequest) throws BimfaceException;

    /**
     * 查询模型集成任务状态
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return {@link FileIntegrateBean} 模型集成状态信息
     * @throws BimfaceException
     */
    FileIntegrateBean getIntegrate(Long integrateId) throws BimfaceException;

    /**
     * 获取集成模型的View Token
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return View Token
     * @throws BimfaceException
     */
    String getViewToken(Long integrateId) throws BimfaceException;
}
