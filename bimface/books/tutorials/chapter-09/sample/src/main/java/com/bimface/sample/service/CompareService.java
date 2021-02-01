package com.bimface.sample.service;

import com.bimface.api.bean.request.modelCompare.CompareRequest;
import com.bimface.api.bean.response.ModelCompareBean;
import com.bimface.data.bean.ModelCompareDiff;
import com.bimface.data.bean.Pagination;
import com.bimface.exception.BimfaceException;

/**
 * 模型对比相关的业务功能实现
 */
public interface CompareService {
    /**
     * 发起模型对比
     * 至少需要设置previousId、followingId和comparedEntityType
     *
     * @param compareRequest {@link CompareRequest} 发起模型对比的参数
     * @return {@link ModelCompareBean} 模型对比状态信息
     * @throws BimfaceException
     */
    ModelCompareBean compare(CompareRequest compareRequest) throws BimfaceException;

    /**
     * 查询模型对比任务状态
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return {@link ModelCompareBean} 模型对比状态信息
     * @throws BimfaceException
     */
    ModelCompareBean getCompare(Long compareId) throws BimfaceException;

    /**
     * 分页获取模型对比的结果
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @param page 页码，默认为0
     * @param pageSize 每页显示的数据条数
     * @return List&lt;ModelCompareDiff&gt; {@link ModelCompareDiff} 模型对比结果
     * @throws BimfaceException
     */
    Pagination<ModelCompareDiff> getModelCompareResult(Long compareId, int page, int pageSize) throws BimfaceException;

    /**
     * 获取模型对比的View Token
     *
     * @param compareId 模型对比ID，发起模型对比时得到
     * @return View Token
     * @throws BimfaceException
     */
    String getViewToken(Long compareId) throws BimfaceException;
}
