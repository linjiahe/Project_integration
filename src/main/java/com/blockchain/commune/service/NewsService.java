package com.blockchain.commune.service;


import com.blockchain.commune.customemapper.Advertisement.AdvertisementCustomMapper;
import com.blockchain.commune.customemapper.news.CustomCommentMapper;
import com.blockchain.commune.customemapper.news.NewsCustomMapper;
import com.blockchain.commune.custommodel.*;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.SystemArgsEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.IdUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    NewsGreatMapper newsGreatMapper;

    @Autowired
    SystemArgsMapper systemArgsMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentReplayMapper commentReplayMapper;

    @Autowired
    CommentGreatMapper commentGreatMapper;

    @Autowired
    NewsCustomMapper newsCustomMapper;

    @Autowired
    CustomCommentMapper customCommentMapper;
    @Autowired
    AdvertisementCustomMapper advertisementCustomMapper;

    public int insertNews(News news) {
        return this.newsMapper.insertSelective(news);
    }

    public int updateNewsByKey(News news) {
        return this.newsMapper.updateByPrimaryKeySelective(news);
    }

    public int updateNewsByKeys(List<String> newsIdList, News news) {
        NewsCriteria newsCriteria = new NewsCriteria();
        newsCriteria.or().andIdIn(newsIdList);
        return this.newsMapper.updateByExampleSelective(news, newsCriteria);
    }

    public int deleteNewsByKey(String newsId) {
        return this.newsMapper.deleteByPrimaryKey(newsId);
    }

    public News selectNewsByKey(String newsId) {
        return this.newsMapper.selectByPrimaryKey(newsId);
    }

    public List<News> selectValidGoods(String newsId) {
        NewsCriteria newsCriteria = new NewsCriteria();
        newsCriteria.or().andIdEqualTo(newsId).
                andOnlineStatusEqualTo((byte) 1);
        return this.newsMapper.selectByExample(newsCriteria);
    }


    public List<News> queryGoods() {

        NewsCriteria qr = new NewsCriteria();
        return this.newsMapper.selectByExample(qr);

    }


    public List<News> queryNewNews(Integer num, String newsId) {

        NewsCriteria qr = new NewsCriteria();
        NewsCriteria.Criteria criteria = qr.createCriteria();

        criteria.andOnlineStatusEqualTo((byte) 1);
        if (!StringUtils.isEmpty(newsId)) {
            criteria.andIdNotEqualTo(newsId);
        }


        String orderString = String.format(" create_time desc limit %d,%d", 0, num);
        qr.setOrderByClause(orderString);

        return this.newsMapper.selectByExample(qr);

    }

    /**
     * 获取推荐列表
     */
    public HashMap<String, Object> selectRecommendedList(Integer page, Integer pageSize) throws CommonException{
        return selectRecommendedList("",null,0,page,pageSize,null,null);
    }

    /**
     * 获取推荐列表
     *
     * @param page
     * @param pageSize
     * @return
     * @throws CommonException
     */
    public HashMap<String, Object> selectRecommendedList(String userId,String phoneOrEmail,int status,Integer page, Integer pageSize,Date beginDate,Date endDate) throws CommonException {
        SystemArgsCriteria sac = new SystemArgsCriteria();
        sac.or().andSysKeyEqualTo(SystemArgsEnum.RECOMMENDED_AMOUNT.toString());
        List<SystemArgs> argsList = this.systemArgsMapper.selectByExample(sac);
        if (CollectionUtils.isEmpty(argsList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "资讯推荐总数未设置");
        }
        Integer count = Integer.valueOf(argsList.get(0).getSysValue());
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        Integer limit = pageSize;
        if (count - page * pageSize < pageSize) {
            limit = count - page * pageSize;
            if (limit < 0) {
                limit = 0;
            }
        }
        String catId = this.categoryService.selectCategoryByCatName("新闻").get(0).getCatId();
        List<News> lst = this.newsCustomMapper.getRecommendedPage(catId, page * pageSize, limit);
        changeListClick(userId,lst);
        if (CollectionUtils.isEmpty(lst)) {
            lst = new ArrayList<News>();
        }
        long pageCount = count;
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, pageCount);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("newsList", lst);
        newMap.put("page", pageObject);
        return newMap;
    }


    public HashMap<String, Object> selectNewsList(String userId, String titleOrExcerpt, String catId, Integer status, Integer page, Integer pageSize, Date beginDate, Date endDate) throws CommonException {

        NewsCriteria qr = new NewsCriteria();

        NewsCriteria.Criteria cr2 = qr.or();
        NewsCriteria.Criteria cr3 = qr.or();


        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }


        if (!StringUtils.isEmpty(titleOrExcerpt)) {
            cr2.andTitleLike("%" + titleOrExcerpt + "%");
            cr3.andExcerptLike("%" + titleOrExcerpt + "%");


        }


        if (!StringUtils.isEmpty(catId)) {
            cr2.andCatIdEqualTo(catId);
            cr3.andCatIdEqualTo(catId);
        }


        if (!StringUtils.isEmpty(status) && status != 0) {
            if (status == 1) {
                cr2.andOnlineStatusEqualTo((byte) 1);
                cr3.andOnlineStatusEqualTo((byte) 1);
            } else if (status == 2) {
                cr2.andOnlineStatusEqualTo((byte) 0);
                cr3.andOnlineStatusEqualTo((byte) 0);
            }
        }


        if (beginDate != null) {
            cr2.andCreateTimeGreaterThanOrEqualTo(beginDate);
            cr3.andCreateTimeGreaterThanOrEqualTo(beginDate);
        }

        if (endDate != null) {
            cr2.andCreateTimeLessThanOrEqualTo(endDate);
            cr3.andCreateTimeLessThanOrEqualTo(endDate);
        }


        long count = this.newsMapper.countByExample(qr);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        qr.setOrderByClause(orderString);

        List<News> lst = this.newsMapper.selectByExample(qr);
        //修改列表的点赞状态
        changeListClick(userId, lst);
        if (CollectionUtils.isEmpty(lst)) {
            lst = new ArrayList<News>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("newsList", lst);
        newMap.put("page", pageObject);
        return newMap;

    }

    /**
     * 修改列表的点赞状态
     * @param userId
     * @param lst
     */
    public void changeListClick(String userId, List<News> lst) {
        if (userId != null) {
            NewsGreatCriteria newsGreatCriteria = new NewsGreatCriteria();
            NewsGreatCriteria.Criteria criteria = newsGreatCriteria.createCriteria();
            criteria.andUserIdEqualTo(userId);

            List<NewsGreat> newsGreats = newsGreatMapper.selectByExample(newsGreatCriteria);
            for (int i = 0; i < lst.size(); i++) {
                News news = lst.get(i);
                String newsId = news.getId();
                for (int j = 0; j < newsGreats.size(); j++) {
                    if (newsId.equals(newsGreats.get(j).getNewsId())) {
                        news.setClick(new Byte("1"));
                        lst.set(i, news);
                    }
                }
            }
        }
    }


    public List<News> selectNewsListByCat(String catId, Integer page, Integer pageSize) {

        NewsCriteria qr = new NewsCriteria();

        NewsCriteria.Criteria criteria = qr.createCriteria();


        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }


        if (!StringUtils.isEmpty(catId)) {
            criteria.andCatIdEqualTo(catId);
        }

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        qr.setOrderByClause(orderString);

        List<News> lst = this.newsMapper.selectByExample(qr);

        if (CollectionUtils.isEmpty(lst)) {
            lst = new ArrayList<News>();
        }


        return lst;

    }


    public HashMap<String, Object> selectNewsListByFilter(String filter, Integer page, Integer pageSize) {
        NewsCriteria newsCriteria = new NewsCriteria();
        NewsCriteria.Criteria criteria = newsCriteria.createCriteria();

        if (!org.apache.commons.lang3.StringUtils.isEmpty(filter)) {
            criteria.andTitleLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = newsMapper.countByExample(newsCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        newsCriteria.setOrderByClause(orderString);

        List<News> news = this.newsMapper.selectByExample(newsCriteria);

        if (CollectionUtils.isEmpty(news)) {
            news = new ArrayList<News>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("news", news);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     * 用户为新闻点赞或取消点赞
     *
     * @param userId
     * @param newsId
     * @throws CommonException
     */
    public boolean getClick(String userId, String newsId) throws CommonException {

        //1、新增点赞记录
        NewsGreatCriteria ncg = new NewsGreatCriteria();
        ncg.createCriteria().andUserIdEqualTo(userId).andNewsIdEqualTo(newsId);

        List<NewsGreat> newsGreats = newsGreatMapper.selectByExample(ncg);
        if (CollectionUtils.isEmpty(newsGreats)) {

            NewsGreat newsGreat = new NewsGreat();
            String id = IdUtil.getId();

            newsGreat.setId(id);
            newsGreat.setUserId(userId);
            newsGreat.setNewsId(newsId);

            int result = newsGreatMapper.insertSelective(newsGreat);
            if (result == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "点赞表新增记录失败");
            }

            //2、修改新闻表点赞数
            getNewsCount(newsId);
            return true;

        } else {
            //若该记录已经存在,由于新增记录默认点赞，此时可取消点赞
            String id1 = newsGreats.get(0).getId();
            int result = newsGreatMapper.deleteByPrimaryKey(id1);

            if (result == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "删除失败");
            }

            //已取消点赞，修改新闻表点赞数
            getNewsCount(newsId);
            return false;
        }
    }

    /**
     * 判断这条新闻是否已被当前用户点赞
     *
     * @param userId
     * @param newsId
     * @return
     * @throws CommonException
     */
    public boolean getUserNewsClick(String userId, String newsId) {

        //查询点赞表是否有记录
        NewsGreatCriteria ncg = new NewsGreatCriteria();
        ncg.createCriteria().andUserIdEqualTo(userId).andNewsIdEqualTo(newsId);

        List<NewsGreat> newsGreats = newsGreatMapper.selectByExample(ncg);
        if (!CollectionUtils.isEmpty(newsGreats)) {
            //有记录表示用户已点赞
            return true;
        } else {
            //当前用户没有点赞记录
            return false;
        }
    }

    /**
     * 查询单条新闻点赞数
     *
     * @param newsId
     * @throws CommonException
     */
    public void getNewsCount(String newsId) throws CommonException {

        NewsGreatCriteria ngc = new NewsGreatCriteria();
        ngc.createCriteria().andNewsIdEqualTo(newsId);

        //获得点赞数
        long count = newsGreatMapper.countByExample(ngc);
        int num = Integer.parseInt(String.valueOf(count));

        //修改新闻列表点赞数以及点赞状态
        News news = newsMapper.selectByPrimaryKey(newsId);
        news.setCollectNum(num);

        int result = newsMapper.updateByPrimaryKeySelective(news);
        if (result == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "修改点赞信息失败");
        }
    }


    /**
     * 新增文章评论
     *
     * @param userId
     * @param newsId
     */
    public Comment insertComment(String userId, String newsId) throws CommonException {
        //评论人
        User user = userMapper.selectByPrimaryKey(userId);
        //评论记录
        Comment newComment = null;

        Comment comment = new Comment();
        String id = IdUtil.getCommentId();
        comment.setCommentId(id);
        comment.setNewsId(newsId);
        comment.setUserId(userId);
        comment.setUserIcon(user.getUserIcon());

        if (user.getNickName() != null) {
            comment.setName(user.getNickName());
        }
        if (user.getLoginName() != null) {
            comment.setName(user.getLoginName());
        }
        if (user.getEmail() != null) {
            comment.setName(user.getEmail());
        }

        int result = commentMapper.insertSelective(comment);
        if (result == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增评论记录失败");
        }
        String commentId = comment.getCommentId();
        newComment = commentMapper.selectByPrimaryKey(commentId);
        return newComment;
    }

    /**
     * 更新评论
     *
     * @param comment
     * @return
     */
    public int updateComment(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    /**
     * 新增回复：一级回复
     *
     * @param userId
     * @param commentId
     */
    public CommentReplay insertReplay(String userId, String commentId) throws CommonException {
        //回复人
        User user = userMapper.selectByPrimaryKey(userId);

        //一级回复记录
        CommentReplay commentReplay = null;

        //一级回复即回复评论
        //新增回复记录
        CommentReplay commentReplay1 = new CommentReplay();
        String id = IdUtil.getCommentReplyId();
        commentReplay1.setReplayId(id);
        commentReplay1.setCommentId(commentId);
        commentReplay1.setUserId(userId);
        commentReplay1.setUserIcon(user.getUserIcon());

        if (user.getNickName() != null) {
            commentReplay1.setName(user.getNickName());
        }
        if (user.getLoginName() != null) {
            commentReplay1.setName(user.getLoginName());
        }
        if (user.getEmail() != null) {
            commentReplay1.setName(user.getEmail());
        }

        int result = commentReplayMapper.insertSelective(commentReplay1);
        if (result == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增回复记录失败");
        }
        //修改评论表回复数
        int count = replayCount(commentId);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        comment.setReplayNum(count);
        int result2 = commentMapper.updateByPrimaryKeySelective(comment);
        if (result2 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "修改回复数失败");
        }

        //成功并返回
        String replayId = commentReplay1.getReplayId();
        commentReplay = commentReplayMapper.selectByPrimaryKey(replayId);
        return commentReplay;
    }

    /**
     * 新增二级回复
     *
     * @param userId
     * @param replayId
     * @return 成功返回二级回复记录（没有回复内容）
     * @throws CommonException
     */
    public CommentReplay insertSecondReplay(String userId, String replayId) throws CommonException {
        //二级回复人
        User user = userMapper.selectByPrimaryKey(userId);

        //二级回复记录
        CommentReplay secondCommentReplay = null;

        //获得一级回复记录
        CommentReplay commentReplay = commentReplayMapper.selectByPrimaryKey(replayId);
        if (commentReplay != null) {
            //1、获得一级回复的评论id
            String commentId = commentReplay.getCommentId();
            //2、获得一级回复的回复人id和回复人用户名
            String reUserId = commentReplay.getUserId();
            User user1 = userMapper.selectByPrimaryKey(reUserId);
            String reName = null;

            if (user1.getNickName() != null) {
                reName = user1.getNickName();
            }
            if (user1.getLoginName() != null) {
                reName = user1.getLoginName();
            }
            if (user1.getEmail() != null) {
                reName = user1.getEmail();
            }

            //3、新增二级回复
            CommentReplay commentReplay1 = new CommentReplay();
            String id = IdUtil.getCommentReplyId();
            commentReplay1.setReplayId(id);
            commentReplay1.setCommentId(commentId);
            commentReplay1.setUserId(userId);
            commentReplay1.setReUserId(reUserId);
            commentReplay1.setReName(reName);

            if (user.getNickName() != null) {
                commentReplay1.setName(user.getNickName());
            }
            if (user.getLoginName() != null) {
                commentReplay1.setName(user.getLoginName());
            }
            if (user.getEmail() != null) {
                commentReplay1.setName(user.getEmail());
            }

            int result = commentReplayMapper.insertSelective(commentReplay1);
            if (result == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增二级回复记录失败");
            }
            //修改评论表回复数
            int count = replayCount(commentId);
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            comment.setReplayNum(count);
            int result2 = commentMapper.updateByPrimaryKeySelective(comment);
            if (result2 == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "修改回复数失败");
            }

            //新增成功并返回二级回复记录
            String secondReplayId = commentReplay1.getReplayId();
            secondCommentReplay = commentReplayMapper.selectByPrimaryKey(secondReplayId);
        }
        return secondCommentReplay;
    }

    /**
     * 更新回复记录
     *
     * @param commentReplay
     * @return
     */
    public int updateCommentReplay(CommentReplay commentReplay) {
        return commentReplayMapper.updateByPrimaryKeySelective(commentReplay);
    }

    /**
     * 查询当前评论的回复数
     *
     * @param commentId
     * @return
     */
    public int replayCount(String commentId) {
        CommentReplayCriteria commentReplayCriteria = new CommentReplayCriteria();
        commentReplayCriteria.createCriteria().andCommentIdEqualTo(commentId);

        int count = Integer.parseInt(String.valueOf(commentReplayMapper.countByExample(commentReplayCriteria)));
        return count;
    }

    /**
     * 新增点赞评论记录、取消点赞
     *
     * @param userId
     * @param commentId
     */
    public boolean insertCommentClick(String userId, String commentId) throws CommonException {
        CommentGreatCriteria commentGreatCriteria = new CommentGreatCriteria();
        commentGreatCriteria.createCriteria().andCommentIdEqualTo(commentId).andUserIdEqualTo(userId);

        List<CommentGreat> commentGreats = commentGreatMapper.selectByExample(commentGreatCriteria);
        if (CollectionUtils.isEmpty(commentGreats)) {
            CommentGreat commentGreat = new CommentGreat();
            String id = IdUtil.getId();
            commentGreat.setId(id);
            commentGreat.setCommentId(commentId);
            commentGreat.setUserId(userId);

            int result = commentGreatMapper.insertSelective(commentGreat);
            if (result == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "点赞评论失败");
            }

            //新增点赞记录成功，修改评论表点赞数
            getCommentClickCount(commentId);
            return true;
        } else {
            //已存在记录，再次点击取消点赞
            int result = commentGreatMapper.deleteByExample(commentGreatCriteria);
            if (result == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "取消点赞失败");
            }
            //取消点赞成功，修改评论表点赞数
            getCommentClickCount(commentId);
            return false;
        }
    }

    /**
     * 获取评论点赞数
     *
     * @param commentId
     */
    public void getCommentClickCount(String commentId) throws CommonException {
        CommentGreatCriteria cgc = new CommentGreatCriteria();
        cgc.createCriteria().andCommentIdEqualTo(commentId);

        long count = commentGreatMapper.countByExample(cgc);
        int clickNum = Integer.parseInt(String.valueOf(count));

        //修改评论表点赞数
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        comment.setClickNum(clickNum);

        int result = commentMapper.updateByPrimaryKeySelective(comment);
        if (result == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "修改评论点赞数失败");
        }
    }

    /**
     * 查询评论列表
     *
     * @param newsId
     * @return
     */
    public HashMap<String, Object> queryCommentList(String newsId, String userId, Integer page, Integer pageSize) {
        CommentCriteria commentCriteria = new CommentCriteria();
        commentCriteria.createCriteria().andNewsIdEqualTo(newsId);

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 3;
        }

        String format = String.format(" click_num desc, create_time desc limit %d,%d", page * pageSize, pageSize);
        commentCriteria.setOrderByClause(format);

        List<Comment> comments = commentMapper.selectByExample(commentCriteria);

        if (userId != null) {
            CommentGreatCriteria commentGreatCriteria = new CommentGreatCriteria();
            commentCriteria.createCriteria().andUserIdEqualTo(userId);

            List<CommentGreat> commentGreats = commentGreatMapper.selectByExample(commentGreatCriteria);
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                String commentId = comment.getCommentId();
                for (int j = 0; j < commentGreats.size(); j++) {
                    CommentGreat commentGreat = commentGreats.get(j);
                    String commentId1 = commentGreat.getCommentId();
                    if (commentId.equals(commentId1)) {
                        comment.setClick(new Byte("1"));
                        comments.set(i, comment);
                    }
                }
            }
        }

        long count = commentMapper.countByExample(commentCriteria);
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commentList", comments);
        hashMap.put("page", pageObject);
        return hashMap;
    }

    /**
     * 查询回复列表
     *
     * @param commentId
     * @return
     */
    public HashMap<String, Object> queryCommentReplayList(String commentId, Integer page, Integer pageSize) {
        CommentReplayCriteria commentReplayCriteria = new CommentReplayCriteria();
        commentReplayCriteria.createCriteria().andCommentIdEqualTo(commentId);

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        String format = String.format(" create_time desc limit %d,%d", page * pageSize, pageSize);
        commentReplayCriteria.setOrderByClause(format);

        List<CommentReplay> commentReplays = commentReplayMapper.selectByExample(commentReplayCriteria);
        long count = commentReplayMapper.countByExample(commentReplayCriteria);
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("replayList", commentReplays);
        hashMap.put("page", pageObject);
        return hashMap;
    }


    /**
     *  查询评论列表 用于后台管理，查询结果中多一个举报数量字段
     * @param filter
     * @param page
     * @param pageSize
     * @return
     */
    public HashMap<String,Object> queryCommentListByFiter(String filter,Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = customCommentMapper.queryCommentListCount(filter);
        List<CustomComment> comments = customCommentMapper.selectCommentListByFilter(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(comments)) {
            comments = new ArrayList<CustomComment>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("comments", comments);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     *  查询评论回复列表 用于后台管理，查询结果中多一个举报数量字段
     * @param filter
     * @param page
     * @param pageSize
     * @return
     */
    public HashMap<String,Object> queryCommentReplayByFiter(String filter,Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = customCommentMapper.queryCommentReplayCount(filter);
        List<CustomCommentReplay> commentReplays = customCommentMapper.selectCommentReplayListByFilter(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(commentReplays)) {
            commentReplays = new ArrayList<CustomCommentReplay>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("commentReplays", commentReplays);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     *  查询广告列表 用于后台管理，查询结果中多一个举报数量字段
     * @param filter
     * @param page
     * @param pageSize
     * @return
     */
    public HashMap<String,Object> queryAdvertListByFiter(String filter,Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = advertisementCustomMapper.queryAdvertListCount(filter);
        List<CustomAdvert> adverts = advertisementCustomMapper.selectAdvertListByFilter(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(adverts)) {
            adverts = new ArrayList<CustomAdvert>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("adverts", adverts);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     *  查询广告回复列表 用于后台管理，查询结果中多一个举报数量字段
     * @param filter
     * @param page
     * @param pageSize
     * @return
     */
    public HashMap<String,Object> queryAdvertReplayByFiter(String filter,Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = advertisementCustomMapper.queryAdvertReplayCount(filter);
        List<CustomAdvertReplay> replays = advertisementCustomMapper.selectAdvertReplayListByFilter(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(replays)) {
            replays = new ArrayList<CustomAdvertReplay>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("replays", replays);
        newMap.put("page", pageObject);
        return newMap;
    }
}
