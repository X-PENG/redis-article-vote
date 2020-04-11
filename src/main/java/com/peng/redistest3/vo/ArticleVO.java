package com.peng.redistest3.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class ArticleVO implements Serializable {
    private static final long serialVersionUID=1;

    private long id;
    private String title;
    private String linkUrl;//文章链接
    private long userId;
    //使用@JsonFormat时间格式化注解，会在@ResponseBody返回json数据的时候，格式化日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime=new Date();//默认是当前时间
    private String tags;//该文章输入哪些标签，保存每个标签id且逗号分隔
    private long votes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "ArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", tags='" + tags + '\'' +
                ", votes=" + votes +
                '}';
    }
}
