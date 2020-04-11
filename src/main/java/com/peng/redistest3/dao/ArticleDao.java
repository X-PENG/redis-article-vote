package com.peng.redistest3.dao;

import com.peng.redistest3.vo.ArticleVO;

import java.util.List;

public interface ArticleDao {
    ArticleVO addArticle(ArticleVO articleVO) throws IllegalAccessException;
    ArticleVO getOne(long id);
    ArticleVO voteOne(long userId,long articleId);
    List<ArticleVO> voteMulti(long userId,long[] articleIds);
    List<ArticleVO> getTopN(int n);
    List<ArticleVO> getLatestN(int n);
    List<ArticleVO> getTopNInTag(int n,long tagId);
    List<ArticleVO> getLatestNInTag(int n,long tagId);
    List<ArticleVO> pageOnTime(int page,int pageSize,int direction);
    List<ArticleVO> pageOnVotes(int page,int pageSize,int direction);
    List<ArticleVO> getArticlesByUser(long userId);

}
