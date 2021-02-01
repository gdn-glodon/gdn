package com.bimface.sample.service;

import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.exception.BimfaceException;

import java.util.List;

/**
 * 离线数据包相关的业务功能实现
 * 每一个用户上传的模型在转换后都可以生成对应的离线数据包，该离线数据包可以下载到本地，独立部署到用户的环境内
 */
public interface OfflineDatabagService {
    /**
     * 生成单模型的离线数据包
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    DatabagDerivativeBean generateTranslateOfflineDatabag(Long fileId) throws BimfaceException;

    /**
     * 生成集成模型的离线数据包
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    DatabagDerivativeBean generateIntegrateOfflineDatabag(Long integrateId) throws BimfaceException;

    /**
     * 生成模型对比的离线数据包
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    DatabagDerivativeBean generateCompareOfflineDatabag(Long compareId) throws BimfaceException;

    /**
     * 查询单模型的离线数据包状态
     *
     * @param fileId 模型ID，文件上传时得到
     * @return List&lt;DatabagDerivativeBean&gt; {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    List<? extends DatabagDerivativeBean> queryTranslateOfflineDatabag(Long fileId) throws BimfaceException;

    /**
     * 查询集成模型的离线数据包状态
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return List&lt;DatabagDerivativeBean&gt; {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    List<? extends DatabagDerivativeBean> queryIntegrateOfflineDatabag(Long integrateId) throws BimfaceException;

    /**
     * 查询模型对比的离线数据包状态
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return List&lt;DatabagDerivativeBean&gt; {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    List<? extends DatabagDerivativeBean> queryCompareOfflineDatabag(Long compareId) throws BimfaceException;

    /**
     * 离线数据包生成成功后，获取离线数据包的下载地址
     *
     * @param fileId 模型ID，文件上传时得到
     * @return 离线数据包下载地址
     * @throws BimfaceException
     */
    String getTranslateOfflineDatabagUrl(Long fileId) throws BimfaceException;

    /**
     * 离线数据包生成成功后，获取离线数据包的下载地址
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return 离线数据包下载地址
     * @throws BimfaceException
     */
    String getIntegrateOfflineDatabagUrl(Long integrateId) throws BimfaceException;

    /**
     * 离线数据包生成成功后，获取离线数据包的下载地址
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return 离线数据包下载地址
     * @throws BimfaceException
     */
    String getCompareOfflineDatabagUrl(Long compareId) throws BimfaceException;
}
