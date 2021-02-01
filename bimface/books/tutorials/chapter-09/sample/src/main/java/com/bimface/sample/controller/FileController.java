package com.bimface.sample.controller;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.TranslateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.exception.BimfaceRuntimeException;
import com.bimface.file.bean.FileBean;
import com.bimface.file.bean.UploadPolicyBean;
import com.bimface.sample.service.BakeService;
import com.bimface.sample.service.FileService;
import com.bimface.sample.service.OfflineDatabagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 单模型相关的接口定义
 * 具体的业务功能在Service层中实现
 * 在注册成为BIMFACE的应用开发者后，要能在浏览器里浏览你的模型或者获取你模型内的BIM数据，
 * 首先需要把你的模型文件上传到BIMFACE。根据不同场景，BIMFACE提供了丰富的文件相关的接口。
 * 在代表模型的源文件上传到BIMFACE后，一般会进行三种API调用操作：
 * 1. 发起模型转换
 * 2. 查询转换状态
 * 3. 如转换成功，获取模型转换后的BIM数据
 */
@RestController
@RequestMapping("file")
public class FileController {

    /**
     * 单模型相关的业务功能实现
     */
    @Autowired
    private FileService fileService;

    /**
     * 离线数据包相关的业务功能实现
     */
    @Autowired
    private OfflineDatabagService offlineDatabagService;

    /**
     * 模型烘焙相关的业务功能实现
     */
    @Autowired
    private BakeService bakeService;

    /**
     * 使用普通文件流上传源文件
     * 不支持表单方式，文件流需要在request body中传递
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @param contentLength 源文件长度
     * @param request 文件流
     * @return {@link FileBean} 文件上传状态信息
     * @throws BimfaceException
     */
    @PutMapping("/upload")
    public FileBean upload(@RequestParam String fileName, @RequestHeader(value = "Content-Length") Long contentLength, HttpServletRequest request) throws BimfaceException {
        // 从RequestBody中获取上传流
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            throw new BimfaceRuntimeException(e);
        }
        return fileService.upload(fileName, contentLength, inputStream);
    }

    /**
     * 外部文件url上传源文件
     * 如果需要上传的文件不在本地，且该文件可以通过指定的HTTP URL可以下载，
     * BIMFACE支持直接传一个外部的HTTP文件URL, BIMFACE会去下载该文件，而无须用户先下载，再上传。
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @param url 文件所在的外部url
     * @return {@link FileBean} 文件上传状态信息
     * @throws BimfaceException
     */
    @PutMapping("/uploadByUrl")
    public FileBean upload(@RequestParam String fileName, @RequestParam String url) throws BimfaceException {
        return fileService.upload(fileName, url);
    }

    /**
     * 文件直传，获取policy凭证
     * 普通的文件上传接口， 文件流会通过BIMFACE的服务器，再流向最终的分布式存储系统，整个上传过程会受BIMFACE服务器的带宽限制，上传速度非最优。
     * 如使用文件直传接口，开发者应用在申请到一个Policy凭证后，可以直接上传文件跟BIMFACE后台的分布式存储系统， 这样上传速度和稳定性都会有提升
     * 使用流程如下：
     * 1. 开发者应用向BIMFACE申请上传Policy请求；
     * 2. BIMFACE返回上传Policy和签名给开发者应用；
     * 3. 开发者应用使用在第二个步骤中获取的URL信息，直接上传文件数据到BIMFACE后端的分布式对象存储。
     * 前端开发人员需要根据该接口返回的policy凭证，自行上传源文件
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @return {@link UploadPolicyBean} 文件直传policy凭证信息
     * @throws BimfaceException
     */
    @GetMapping("/policy")
    public UploadPolicyBean getPolicy(@RequestParam String fileName) throws BimfaceException {
        return fileService.getPolicy(fileName);
    }

    /**
     * 文件直传，直接通过policy上传源文件
     * 如果用户不希望自己处理policy，可以直接调用该接口
     * 该接口会自行获取policy，之后根据policy的信息直接上传源文件，不需要用户参与
     *
     * @param fileName 用户定义的文件名，使用URL编码（UTF-8），最多256个字符
     * @param contentLength 源文件长度
     * @param request 文件流
     * @return {@link FileBean} 文件上传状态信息
     * @throws BimfaceException
     */
    @PutMapping("/uploadByPolicy")
    public FileBean uploadByPolicy(@RequestParam String fileName, @RequestHeader(value = "Content-Length") Long contentLength, HttpServletRequest request) throws BimfaceException {
        // 获取上传流
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            throw new BimfaceRuntimeException(e);
        }
        return fileService.uploadByPolicy(fileName, contentLength, inputStream);
    }

    /**
     * 发起模型转换
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link FileTranslateBean} 模型转换状态信息
     * @throws BimfaceException
     */
    @PutMapping("/translate")
    public FileTranslateBean translate(@RequestParam Long fileId) throws BimfaceException {
        return fileService.translate(fileId);
    }

    /**
     * 获取转换状态
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link FileTranslateBean} 模型转换状态信息
     * @throws BimfaceException
     */
    @GetMapping("/translate")
    public FileTranslateBean getTranslate(@RequestParam Long fileId) throws BimfaceException {
        return fileService.getTranslate(fileId);
    }

    /**
     * 获取文件下载链接
     * 应用通过该接口获取文件的下载地址，下载地址有效时间是5分钟
     *
     * @param fileId 模型ID，文件上传时得到
     * @return 源文件下载地址
     * @throws BimfaceException
     */
    @GetMapping("/downloadUrl")
    public String getDownloadUrl(@RequestParam Long fileId) throws BimfaceException {
        return fileService.getDownloadUrl(fileId);
    }

    /**
     * 获取模型的View Token
     * View Token代表对单个模型/集成模型/模型对比的临时的访问凭证，只能访问对应模型的数据接口
     * 有效期为12小时
     * 把View Token传入前端JavaScript组件提供的接口中，即可加载和浏览文件所包含的三维模型或二维图纸。
     * View Token的使用方法是在调用对应的数据接口的时候，添加一个查询参数（Query parameter）:
     *   view_token={your_view_token}
     * 只有在文件转换或模型集成任务成功以后，才能获取View Token。
     *
     * @param fileId 模型ID，文件上传时得到
     * @return View Token
     * @throws BimfaceException
     */
    @GetMapping("/viewToken")
    public String getViewToken(@RequestParam Long fileId) throws BimfaceException {
        return fileService.getViewToken(fileId);
    }

    /**
     * 生成单模型的离线数据包
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    @PutMapping("/databag")
    public DatabagDerivativeBean offlineDatabag(@RequestParam Long fileId) throws BimfaceException {
        return offlineDatabagService.generateTranslateOfflineDatabag(fileId);
    }

    /**
     * 查询单模型的离线数据包状态
     *
     * @param fileId 模型ID，文件上传时得到
     * @return List&lt;DatabagDerivativeBean&gt; {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    @GetMapping("/databag")
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(@RequestParam Long fileId) throws BimfaceException {
        return offlineDatabagService.queryTranslateOfflineDatabag(fileId);
    }

    /**
     * 离线数据包生成成功后，获取离线数据包的下载地址
     *
     * @param fileId 模型ID，文件上传时得到
     * @return 离线数据包下载地址
     * @throws BimfaceException
     */
    @GetMapping("/databagUrl")
    public String getOfflineDatabagUrl(@RequestParam Long fileId) throws BimfaceException {
        return offlineDatabagService.getTranslateOfflineDatabagUrl(fileId);
    }

    /**
     * 对单模型进行烘焙
     *
     * @param fileId 模型ID，文件上传时得到
     * @return {@link TranslateDatabagDerivativeBean} 单模型烘焙数据包状态
     * @throws BimfaceException
     */
    @PutMapping("/bake")
    public TranslateDatabagDerivativeBean bake(@RequestParam Long fileId) throws BimfaceException {
        return bakeService.bakeTranslate(fileId);
    }

    /**
     * 查询单模型的烘焙数据包状态
     *
     * @param fileId 模型ID，文件上传时得到
     * @return List&lt;TranslateDatabagDerivativeBean&gt; {@link TranslateDatabagDerivativeBean} 单模型烘焙数据包状态
     * @throws BimfaceException
     */
    @GetMapping("/bake")
    public List<TranslateDatabagDerivativeBean> getBake(@RequestParam Long fileId) throws BimfaceException {
        return bakeService.getBakeTranslate(fileId);
    }
}
