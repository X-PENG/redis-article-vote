package com.peng.redistest3.bean;

import com.peng.redistest3.vo.ArticleVO;
import com.peng.redistest3.vo.TagVO;
import com.peng.redistest3.vo.UserVO;

import java.io.Serializable;
import java.util.List;

public class ArticleBean implements Serializable {
    private static final long serialVersionUID=1;

    private ArticleVO articleVO;//文章基本信息
    private List<TagVO> tags;//有哪些标签
    private UserVO user;//作者

    public ArticleVO getArticleVO() {
        return articleVO;
    }

    public void setArticleVO(ArticleVO articleVO) {
        this.articleVO = articleVO;
    }

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
