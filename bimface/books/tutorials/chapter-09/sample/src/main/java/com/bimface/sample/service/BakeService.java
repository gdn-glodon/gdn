package com.bimface.sample.service;

import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;

import java.util.List;

/**
 * 模型烘焙相关的业务功能实现
 * BIMFACE支持在模型完成转换后，再对其进行烘焙，以获得更具真实感的光影效果。
 * 使用流程如下：
 * 1. 创建模型烘焙；
 * 2. 查询模型烘焙状态；
 * 3. 若烘焙任务状态为完成，则获取View Token以浏览模型。
 */
public interface BakeService {
    /**
     * 为单模型创建烘焙
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link TranslateDatabagDerivativeBean} 单模型烘焙数据包状态
     * @throws BimfaceException
     */
    TranslateDatabagDerivativeBean bakeTranslate(Long fileId) throws BimfaceException;

    /**
     * 对集成模型进行烘焙
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return {@link IntegrateDatabagDerivativeBean} 集成模型烘焙数据包状态
     * @throws BimfaceException
     */
    IntegrateDatabagDerivativeBean bakeIntegrate(Long integrateId) throws BimfaceException;

    /**
     * 查询单模型的烘焙数据包状态
     *
     * @param fileId 模型ID，文件上传时得到
     * @return List&lt;TranslateDatabagDerivativeBean&gt; {@link TranslateDatabagDerivativeBean} 单模型烘焙数据包状态
     * @throws BimfaceException
     */
    List<TranslateDatabagDerivativeBean> getBakeTranslate(Long fileId) throws BimfaceException;

    /**
     * 查询集成模型的烘焙数据包状态
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return List&lt;IntegrateDatabagDerivativeBean&gt; {@link IntegrateDatabagDerivativeBean} 集成模型烘焙数据包状态
     * @throws BimfaceException
     */
    List<IntegrateDatabagDerivativeBean> getBakeIntegrate(Long integrateId) throws BimfaceException;
}
