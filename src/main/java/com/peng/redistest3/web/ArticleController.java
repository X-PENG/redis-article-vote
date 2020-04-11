package com.peng.redistest3.web;

import com.peng.redistest3.bean.ArticleBean;
import com.peng.redistest3.service.ArticleService;
import com.peng.redistest3.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/publish")
    public ArticleBean publish(ArticleVO articleVO) throws IllegalAccessException {
        return articleService.addArticle(articleVO);
    }
    @GetMapping("/get")
    public ArticleBean getOne(long id){
        return articleService.getOne(id);
    }

    @GetMapping("/vote/{userId}/{articleId}")
    public ArticleBean voteOne(@PathVariable("userId") long userId,@PathVariable("articleId") long articleId){
        return articleService.voteOne(userId,articleId);
    }

    @GetMapping("/vote/{userId}")
    public List<ArticleBean> voteMulti(@PathVariable("userId") long userId, long[] aId){
        return articleService.voteMulti(userId,aId);
    }

    @GetMapping("/votesTop/{n}")
    public List<ArticleBean> votesTop(@PathVariable int n){
        return articleService.getTopN(n);
    }

    @GetMapping("/latest/{n}")
    public List<ArticleBean> latest(@PathVariable int n){
        return articleService.getLatestN(n);
    }

    @GetMapping("/pageOnVotes")
    public List<ArticleBean> pageOnVotes(int page,int pageSize,int direction){
        return articleService.pageOnVotes(page,pageSize,direction);
    }

    @GetMapping("/pageOnTime")
    public List<ArticleBean> pageOnTime(int page,int pageSize,int direction){
        return articleService.pageOnTime(page,pageSize,direction);
    }

    @GetMapping("/votesTopOnTag/{tagId}/{n}")
    public List<ArticleBean> votesTopOnTag(@PathVariable long tagId,@PathVariable int n){
        return articleService.getTopNInTag(n,tagId);
    }

    @GetMapping("/latestOnTag/{tagId}/{n}")
    public List<ArticleBean> latestOnTag(@PathVariable long tagId,@PathVariable int n){
        return articleService.getLatestNInTag(n,tagId);
    }

    //查询该用户的所有文章，且按时间降序
    @GetMapping("/getAllByUser/{userId}")
    public List<ArticleBean> latestOnTag(@PathVariable long userId){
        return articleService.getArticlesByUser(userId);
    }
}
