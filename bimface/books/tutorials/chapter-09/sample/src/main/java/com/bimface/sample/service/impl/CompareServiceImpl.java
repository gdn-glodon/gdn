package com.bimface.sample.service.impl;

import com.bimface.api.bean.request.modelCompare.CompareRequest;
import com.bimface.api.bean.response.ModelCompareBean;
import com.bimface.data.bean.ModelCompareDiff;
import com.bimface.data.bean.Pagination;
import com.bimface.exception.BimfaceException;
import com.bimface.sample.service.CompareService;
import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模型对比相关的业务功能实现
 * 在需要使用sdk处注入BimfaceClient
 * 调用sdk方法的同时，可以根据需要加入业务代码
 */
@Service
public class CompareServiceImpl implements CompareService {

    /**
     * 注入BimfaceClient
     */
    @Autowired
    private BimfaceClient bimfaceClient;

    @Override
    public ModelCompareBean compare(CompareRequest compareRequest) throws BimfaceException {
        // 发起模型对比，直接传入前端传递的CompareRequest
        // 接口调用成功后，可以从返回结果中获取compareId，用于调用模型对比的其他功能，请妥善保存
        // 开发人员可以进行二次开发，将compareId记录到数据库中
        // compare接口已弃用，请使用compareV2接口
        ModelCompareBean modelCompareBean = bimfaceClient.compareV2(compareRequest);
        return modelCompareBean;
    }

    @Override
    public ModelCompareBean getCompare(Long compareId) throws BimfaceException {
        // 根据compareId查询模型对比状态
        ModelCompareBean compareInfo = bimfaceClient.getCompareInfo(compareId);
        return compareInfo;
    }

    @Override
    public Pagination<ModelCompareDiff> getModelCompareResult(Long compareId, int page, int pageSize) throws BimfaceException {
        // 根据compareId获取模型对比结果
        // 由于是分页查询，需要指定page和pageSize，即页码和每页数据量
        // 该接口还可以指定构件的family和elementName，用于查询指定构件的对比结果，开发人员可以根据需要向接口添加这两个参数
        Pagination<ModelCompareDiff> modelCompareResult = bimfaceClient.getModelCompareResult(compareId, null, null, page, pageSize);
        return modelCompareResult;
    }

    @Override
    public String getViewToken(Long compareId) throws BimfaceException {
        // 根据compareId获取模型对比的View Token
        String viewToken = bimfaceClient.getViewTokenByCompareId(compareId);
        return viewToken;
    }
}
