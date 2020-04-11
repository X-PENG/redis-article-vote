package com.peng.redistest3.service;

import com.peng.redistest3.bean.ArticleBean;
import com.peng.redistest3.vo.ArticleVO;

import java.util.List;

public interface ArticleService {
    ArticleBean addArticle(ArticleVO articleVO) throws IllegalAccessException;
    ArticleBean getOne(long id);
    ArticleBean voteOne(long userId,long articleId);
    List<ArticleBean> voteMulti(long userId, long[] articleIds);
    List<ArticleBean> getTopN(int n);
    List<ArticleBean> getLatestN(int n);
    List<ArticleBean> getTopNInTag(int n,long tagId);
    List<ArticleBean> getLatestNInTag(int n,long tagId);
    List<ArticleBean> pageOnTime(int page,int pageSize,int direction);
    List<ArticleBean> pageOnVotes(int page,int pageSize,int direction);

    List<ArticleBean> getArticlesByUser(long userId);
}
