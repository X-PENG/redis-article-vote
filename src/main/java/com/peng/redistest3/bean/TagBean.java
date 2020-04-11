package com.peng.redistest3.bean;

import com.peng.redistest3.vo.ArticleVO;
import com.peng.redistest3.vo.TagVO;

import java.util.List;

public class TagBean {
    private static final long serialVersionUID=1;

    private TagVO tagVO;//标签基本信息
    private List<ArticleVO> articles;//有哪些文章。按顺序排序

    public TagVO getTagVO() {
        return tagVO;
    }

    public void setTagVO(TagVO tagVO) {
        this.tagVO = tagVO;
    }

    public List<ArticleVO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleVO> articles) {
        this.articles = articles;
    }
}
