package com.bimface.sample.controller;

import com.bimface.api.bean.request.integrate.FileIntegrateRequest;
import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.api.bean.response.databagDerivative.IntegrateDatabagDerivativeBean;
import com.bimface.exception.BimfaceException;
import com.bimface.sample.service.BakeService;
import com.bimface.sample.service.IntegrateService;
import com.bimface.sample.service.OfflineDatabagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型集成相关的接口定义
 * 具体的业务功能在Service层中实现
 * 如果想要在云端浏览整个项目工程，就需要把整个项目的多个单模型集成起来一起展示
 * 在集成过程中对构件按单体，楼层，专业，构件分类，系统类型等进行分类或映射
 */
@RestController
@RequestMapping("integrate")
public class IntegrateController {

    /**
     * 模型集成相关的业务功能实现
     */
    @Autowired
    private IntegrateService integrateService;

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
     * 发起模型集成
     * 至少需要设置参与集成的单模型的文件ID
     *
     * @param integrateRequest {@link FileIntegrateRequest} 发起模型集成的参数
     * @return {@link FileIntegrateBean} 模型集成状态信息
     * @throws BimfaceException
     */
    @PutMapping("")
    public FileIntegrateBean integrate(@RequestBody FileIntegrateRequest integrateRequest) throws BimfaceException {
        // 集成参数在FileIntegrateRequest中定义，由调用者传递
        return integrateService.integrate(integrateRequest);
    }

    /**
     * 查询模型集成任务状态
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return {@link FileIntegrateBean} 模型集成状态信息
     * @throws BimfaceException
     */
    @GetMapping("")
    public FileIntegrateBean getIntegrate(@RequestParam Long integrateId) throws BimfaceException {
        // 根据integrateId查询该集成的状态
        return integrateService.getIntegrate(integrateId);
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
     * @param integrateId 集成ID，发起模型集成时得到
     * @return View Token
     * @throws BimfaceException
     */
    @GetMapping("/viewToken")
    public String getViewToken(@RequestParam Long integrateId) throws BimfaceException {
        return integrateService.getViewToken(integrateId);
    }

    /**
     * 生成集成模型的离线数据包
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    @PutMapping("/databag")
    public DatabagDerivativeBean offlineDatabag(@RequestParam Long integrateId) throws BimfaceException {
        return offlineDatabagService.generateIntegrateOfflineDatabag(integrateId);
    }

    /**
     * 查询集成模型的离线数据包状态
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return List&lt;DatabagDerivativeBean&gt; {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    @GetMapping("/databag")
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(@RequestParam Long integrateId) throws BimfaceException {
        return offlineDatabagService.queryIntegrateOfflineDatabag(integrateId);
    }

    /**
     * 离线数据包生成成功后，获取离线数据包的下载地址
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return 离线数据包下载地址
     * @throws BimfaceException
     */
    @GetMapping("/databagUrl")
    public String getOfflineDatabagUrl(@RequestParam Long integrateId) throws BimfaceException {
        return offlineDatabagService.getIntegrateOfflineDatabagUrl(integrateId);
    }

    /**
     * 对集成模型进行烘焙
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return {@link IntegrateDatabagDerivativeBean} 集成模型烘焙数据包状态
     * @throws BimfaceException
     */
    @PutMapping("/bake")
    public IntegrateDatabagDerivativeBean bake(@RequestParam Long integrateId) throws BimfaceException {
        return bakeService.bakeIntegrate(integrateId);
    }

    /**
     * 查询集成模型的烘焙数据包状态
     *
     * @param integrateId 集成ID，发起模型集成时得到
     * @return List&lt;IntegrateDatabagDerivativeBean&gt; {@link IntegrateDatabagDerivativeBean} 集成模型烘焙数据包状态
     * @throws BimfaceException
     */
    @GetMapping("/bake")
    public List<IntegrateDatabagDerivativeBean> getBake(@RequestParam Long integrateId) throws BimfaceException {
        return bakeService.getBakeIntegrate(integrateId);
    }
}
