package com.blockchain.commune.service;

import com.blockchain.commune.enums.CommentTypeEnum;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.ReportTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import com.blockchain.commune.mapper.AdReplayMapper;
import com.blockchain.commune.mapper.CommentMapper;
import com.blockchain.commune.mapper.ReportMapper;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.IdUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    AdReplayMapper adReplayMapper;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    CommentReplayMapper commentReplayMapper;

    @Autowired
    AdvertisementMapper advertisementMapper;

    /**
     * 举报评论
     * @param userId   当前用户
     * @param typeId  评论id/回复id/广告回复id
     * @param commentTypeEnum 评论类型
     * @param reportTypeEnum 举报类型
     * @return
     * @throws CommonException
     */
    public int insertReport(String userId, String typeId, CommentTypeEnum commentTypeEnum, ReportTypeEnum reportTypeEnum)throws CommonException{
        //要举报的评论
        Comment comment = commentMapper.selectByPrimaryKey(typeId);
        //要举报的评论的回复
        CommentReplay commentReplay = commentReplayMapper.selectByPrimaryKey(typeId);
        //要举报的广告的回复
        AdReplay adReplay = adReplayMapper.selectByPrimaryKey(typeId);
        //要举报的广告
        Advertisement advertisement = advertisementMapper.selectByPrimaryKey(typeId);
        //举报
        Report report = new Report();
        String id = IdUtil.getReportId();
        report.setReportId(id);
        report.setTypeId(typeId);
        report.setType(commentTypeEnum.toString());
        report.setReportType(reportTypeEnum.toString());
        report.setUserId(userId);

        //评论的内容
        if (report.getType().equals(CommentTypeEnum.COMMENT.toString())){
            report.setTypeContext(comment.getCommentContext());
        }
        //评论的回复内容
        if (report.getType().equals(CommentTypeEnum.COMMENTREPLAY.toString())){
            report.setTypeContext(commentReplay.getReplayContext());
        }
        //广告的回复内容
        if (report.getType().equals(CommentTypeEnum.ADREPLAY.toString())){
            report.setTypeContext(adReplay.getReplayContext());
        }
        //广告的内容
        if (report.getType().equals(CommentTypeEnum.ADVERTISEMENT.toString())){
            report.setTypeContext(advertisement.getAdContext());
        }

        int result = reportMapper.insertSelective(report);
        if (result == 0){
            throw new CommonException(ErrorCodeEnum.DBERROR,"新增举报记录失败");
        }
        return result;
    }

    public HashMap<String, Object> selectReportByFilter(String filter, Integer page, Integer pageSize) {
        ReportCriteria reportCriteria = new ReportCriteria();
        ReportCriteria.Criteria criteria = reportCriteria.createCriteria();

        if (!StringUtils.isEmpty(filter)) {
            criteria.andTypeIdLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = reportMapper.countByExample(reportCriteria);

        String orderString = String.format(" sys_key desc limit %d,%d ", page * pageSize, pageSize);

        reportCriteria.setOrderByClause(orderString);

        List<Report> reports = this.reportMapper.selectByExample(reportCriteria);

        if (CollectionUtils.isEmpty(reports)) {
            reports = new ArrayList<Report>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("reports", reports);
        newMap.put("page", pageObject);

        return newMap;
    }
}
