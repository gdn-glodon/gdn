package com.bimface.sample.service.impl;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.file.bean.UploadPolicyBean;
import com.bimface.sample.service.FileService;
import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 单模型相关的业务功能实现
 * 在需要使用sdk处注入BimfaceClient
 * 调用sdk方法的同时，可以根据需要加入业务代码
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 注入BimfaceClient
     */
    @Autowired
    private BimfaceClient bimfaceClient;

    @Override
    public FileBean upload(String fileName, Long contentLength, InputStream inputStream) throws BimfaceException {
        // 直接通过文件流上传源文件
        // 接口调用成功后，可以从返回结果中获取fileId，用于调用模型相关的其他功能，请妥善保存
        // 开发人员可以进行二次开发，将fileId记录到数据库中
        FileBean upload = bimfaceClient.upload(fileName, contentLength, inputStream);
        return upload;
    }

    @Override
    public FileBean upload(String fileName, String url) throws BimfaceException {
        // 通过外部url上传源文件
        // 接口调用成功后，可以从返回结果中获取fileId，用于调用模型相关的其他功能，请妥善保存
        // 开发人员可以进行二次开发，将fileId记录到数据库中
        FileBean upload = bimfaceClient.upload(fileName, url);
        return upload;
    }

    @Override
    public UploadPolicyBean getPolicy(String fileName) throws BimfaceException {
        // 获取文件直传需要的policy信息，不实际上传文件
        UploadPolicyBean policy = bimfaceClient.getPolicy(fileName);
        return policy;
    }

    @Override
    public FileBean uploadByPolicy(String fileName, Long contentLength, InputStream inputStream)
        throws BimfaceException {
        // 直接进行文件直传
        // sdk会自行向BIMFACE申请上传policy，并根据得到的policy直接上传文件数据到BIMFACE后端的分布式对象存储
        // 接口调用成功后，可以从返回结果中获取fileId，用于调用模型相关的其他功能，请妥善保存
        // 开发人员可以进行二次开发，将fileId记录到数据库中
        FileBean fileBean = bimfaceClient.uploadByPolicy(fileName, contentLength, inputStream);
        return fileBean;
    }

    @Override
    public FileTranslateBean translate(Long fileId) throws BimfaceException {
        // 根据已上传文件的fileId发起模型转换
        FileTranslateBean translate = bimfaceClient.translate(fileId);
        return translate;
    }

    @Override
    public FileTranslateBean getTranslate(Long fileId) throws BimfaceException {
        // 根据已上传文件的fileId查询该模型的转换状态
        FileTranslateBean translate = bimfaceClient.getTranslate(fileId);
        return translate;
    }

    @Override
    public String getDownloadUrl(Long fileId) throws BimfaceException {
        // 根据已上传文件的fileId获取源文件的下载地址
        String downloadUrl = bimfaceClient.getDownloadUrl(fileId);
        return downloadUrl;
    }

    @Override
    public String getViewToken(Long fileId) throws BimfaceException {
        // 根据fileId获取模型的View Token
        String viewToken = bimfaceClient.getViewTokenByFileId(fileId);
        return viewToken;
    }
}
