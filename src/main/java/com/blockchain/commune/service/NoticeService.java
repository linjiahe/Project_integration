package com.blockchain.commune.service;

import com.blockchain.commune.enums.NoticePublishStatusEnum;
import com.blockchain.commune.enums.NoticeTypeEnum;
import com.blockchain.commune.mapper.NoticeMapper;
import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.model.MarketTagCriteria;
import com.blockchain.commune.model.Notice;
import com.blockchain.commune.model.NoticeCriteria;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    public int insertNotice(Notice notice) {
        return this.noticeMapper.insertSelective(notice);
    }

    public int updateNotice(Notice notice) {
        return this.noticeMapper.updateByPrimaryKeySelective(notice);
    }

    public int deleteNoticeByKey(String noticeId) {
        return this.noticeMapper.deleteByPrimaryKey(noticeId);
    }

    public Notice selectNoticeByKey(String noticeId) {
        return this.noticeMapper.selectByPrimaryKey(noticeId);
    }

    public List<Notice> selectNoticeByPublisher(String publisher) {
        NoticeCriteria noticeCriteria = new NoticeCriteria();
        noticeCriteria.or().andPublisherEqualTo(publisher);
        return this.noticeMapper.selectByExample(noticeCriteria);
    }

    //前台业务逻辑

    /**
     * 查询已发布的相关通知类型的列表
     * @param noticeType
     * @return
     */
    public List<Notice> selectNoticeByNoticeType(String noticeType) {
        NoticeCriteria noticeCriteria = new NoticeCriteria();
        noticeCriteria.setOrderByClause("publish_time desc");
        noticeCriteria.or().andNoticeTypeEqualTo(noticeType).andPublishStatusEqualTo(NoticePublishStatusEnum.PUBLISHED.toString());
        return this.noticeMapper.selectByExample(noticeCriteria);
    }

    public HashMap<String, Object> selectNotice(String filter, Integer page, Integer pageSize) {
        NoticeCriteria tagCriteria = new NoticeCriteria();
        NoticeCriteria.Criteria criteria = tagCriteria.createCriteria();

        if (!StringUtils.isEmpty(filter)) {
            criteria.andTitleLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = noticeMapper.countByExample(tagCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        tagCriteria.setOrderByClause(orderString);

        List<Notice> notices = this.noticeMapper.selectByExample(tagCriteria);

        if (CollectionUtils.isEmpty(notices)) {
            notices = new ArrayList<Notice>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("notices", notices);
        newMap.put("page", pageObject);


        return newMap;
    }
}
