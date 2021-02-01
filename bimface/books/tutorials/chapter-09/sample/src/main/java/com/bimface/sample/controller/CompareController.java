package com.bimface.sample.controller;

import com.bimface.api.bean.request.modelCompare.CompareRequest;
import com.bimface.api.bean.response.ModelCompareBean;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.data.bean.ModelCompareDiff;
import com.bimface.data.bean.Pagination;
import com.bimface.exception.BimfaceException;
import com.bimface.sample.service.CompareService;
import com.bimface.sample.service.OfflineDatabagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型对比相关的接口定义
 * 具体的业务功能在Service层中实现
 * 模型对比可以对两个文件/模型进行差异性分析，确定两个文件/模型之间构件的几何和属性差异，
 * 包括增加的构件、删除的构件和修改的构件。 模型对应可以用于进行文件/模型的版本对比。
 * 1. 几何对比
 * 几何数据的对比的粒度为构件级，即只要构件的某一部分几何数据或材质发生改变，就认为整个构件发生变化。
 * 2. 属性对比
 * 属性对比的粒度为构件的属性级，即对两个文件中的相同ID的构件挨个属性值进行对比。
 */
@RestController
@RequestMapping("compare")
public class CompareController {

    /**
     * 模型对比相关的业务功能实现
     */
    @Autowired
    private CompareService compareService;

    /**
     * 离线数据包相关的业务功能实现
     */
    @Autowired
    private OfflineDatabagService offlineDatabagService;

    /**
     * 发起模型对比
     * 至少需要设置previousId、followingId和comparedEntityType
     *
     * @param compareRequest {@link CompareRequest} 发起模型对比的参数
     * @return {@link ModelCompareBean} 模型对比状态信息
     * @throws BimfaceException
     */
    @PostMapping("")
    public ModelCompareBean compare(@RequestBody CompareRequest compareRequest) throws BimfaceException {
        // 对比参数在CompareRequest中定义，由调用者传递
        return compareService.compare(compareRequest);
    }

    /**
     * 查询模型对比任务状态
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return {@link ModelCompareBean} 模型对比状态信息
     * @throws BimfaceException
     */
    @GetMapping("")
    public ModelCompareBean getCompare(@RequestParam Long compareId) throws BimfaceException {
        return compareService.getCompare(compareId);
    }

    /**
     * 分页获取模型对比的结果
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @param page 页码，默认为0
     * @param pageSize 每页显示的数据条数
     * @return List&lt;ModelCompareDiff&gt; {@link ModelCompareDiff} 模型对比结果
     * @throws BimfaceException
     */
    @GetMapping("/diff")
    public Pagination<ModelCompareDiff> getModelCompareResult(@RequestParam Long compareId,
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "20") Integer pageSize) throws BimfaceException {
        // 根据compareId分页查询该对比的结果，page、pageSize分别为页码和每页的大小
        return compareService.getModelCompareResult(compareId, page, pageSize);
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
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return View Token
     * @throws BimfaceException
     */
    @GetMapping("/viewToken")
    public String getViewToken(@RequestParam Long compareId) throws BimfaceException {
        return compareService.getViewToken(compareId);
    }

    /**
     * 生成模型对比的离线数据包
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    @PutMapping("/databag")
    public DatabagDerivativeBean offlineDatabag(@RequestParam Long compareId) throws BimfaceException {
        return offlineDatabagService.generateCompareOfflineDatabag(compareId);
    }

    /**
     * 查询模型对比的离线数据包状态
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return List&lt;DatabagDerivativeBean&gt; {@link DatabagDerivativeBean} 离线数据包状态信息
     * @throws BimfaceException
     */
    @GetMapping("/databag")
    public List<? extends DatabagDerivativeBean> queryOfflineDatabag(@RequestParam Long compareId)
        throws BimfaceException {
        return offlineDatabagService.queryCompareOfflineDatabag(compareId);
    }

    /**
     * 离线数据包生成成功后，获取离线数据包的下载地址
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return 离线数据包下载地址
     * @throws BimfaceException
     */
    @GetMapping("/databagUrl")
    public String getOfflineDatabagUrl(@RequestParam Long compareId) throws BimfaceException {
        return offlineDatabagService.getCompareOfflineDatabagUrl(compareId);
    }
}
