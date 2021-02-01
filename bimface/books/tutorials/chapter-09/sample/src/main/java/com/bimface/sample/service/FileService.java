package com.bimface.sample.service;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.file.bean.UploadPolicyBean;

import java.io.InputStream;

/**
 * 单模型相关的业务功能实现
 */
public interface FileService {
    /**
     * 使用普通文件流上传源文件
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @param contentLength 源文件长度
     * @param inputStream 文件流
     * @return {@link FileBean} 文件上传状态信息
     * @throws BimfaceException
     */
    FileBean upload(String fileName, Long contentLength, InputStream inputStream) throws BimfaceException;

    /**
     * 外部文件url上传源文件
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @param url 文件所在的外部url
     * @return {@link FileBean} 文件上传状态信息
     * @throws BimfaceException
     */
    FileBean upload(String fileName, String url) throws BimfaceException;

    /**
     * 文件直传，获取policy凭证
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @return {@link UploadPolicyBean} 文件直传policy凭证信息
     * @throws BimfaceException
     */
    UploadPolicyBean getPolicy(String fileName) throws BimfaceException;

    /**
     * 文件直传，直接通过policy上传源文件
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @param contentLength 源文件长度
     * @param inputStream 文件流
     * @return {@link FileBean} 文件上传状态信息
     * @throws BimfaceException
     */
    FileBean uploadByPolicy(String fileName, Long contentLength, InputStream inputStream) throws BimfaceException;

    /**
     * 发起模型转换
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link FileTranslateBean} 模型转换状态信息
     * @throws BimfaceException
     */
    FileTranslateBean translate(Long fileId) throws BimfaceException;

    /**
     * 获取转换状态
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link FileTranslateBean} 模型转换状态信息
     * @throws BimfaceException
     */
    FileTranslateBean getTranslate(Long fileId) throws BimfaceException;

    /**
     * 获取源文件下载地址
     *
     * @param fileId 模型ID，文件上传时得到
     * @return 源文件下载地址
     * @throws BimfaceException
     */
    String getDownloadUrl(Long fileId) throws BimfaceException;

    /**
     * 获取单模型的View Token
     *
     * @param fileId 模型ID，文件上传时得到
     * @return View Token
     * @throws BimfaceException
     */
    String getViewToken(Long fileId) throws BimfaceException;
}
