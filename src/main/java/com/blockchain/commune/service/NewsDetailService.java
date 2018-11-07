package com.blockchain.commune.service;





import com.blockchain.commune.mapper.NewsDetailMapper;
import com.blockchain.commune.model.NewsDetail;
import com.blockchain.commune.model.NewsDetailCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsDetailService {

    @Autowired
    NewsDetailMapper newsDetailMapper;

    public int insertNewsDetail(NewsDetail newsDetail) {
        return this.newsDetailMapper.insertSelective(newsDetail);
    }

    public int updateNewsDetailByKey(NewsDetail newsDetail) {
        return this.newsDetailMapper.updateByPrimaryKeySelective(newsDetail);
    }

    public int deleteNewsDetailByKey(String newsId) {
        NewsDetail newsDetail = this.newsDetailMapper.selectByPrimaryKey(newsId);
        if (newsDetail == null) {
            return 1;
        }
        return this.newsDetailMapper.deleteByPrimaryKey(newsId);
    }

    public NewsDetail selectNewsDetailByKey(String newsId) {
        return this.newsDetailMapper.selectByPrimaryKey(newsId);
    }

    public List<NewsDetail> selectNewsDetail() {
        NewsDetailCriteria newsDetailCriteria = new NewsDetailCriteria();
        return this.newsDetailMapper.selectByExample(newsDetailCriteria);
    }


}
