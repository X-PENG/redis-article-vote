package com.peng.redistest3.service.imp;

import com.peng.redistest3.bean.ArticleBean;
import com.peng.redistest3.dao.ArticleDao;
import com.peng.redistest3.dao.TagDao;
import com.peng.redistest3.dao.UserDao;
import com.peng.redistest3.service.ArticleService;
import com.peng.redistest3.vo.ArticleVO;
import com.peng.redistest3.vo.TagVO;
import com.peng.redistest3.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleDao articleDao;

    @Override
    public ArticleBean addArticle(ArticleVO articleVO) throws IllegalAccessException {
        articleVO=articleDao.addArticle(articleVO);
        return generateBean(articleVO);
    }

    @Override
    public ArticleBean getOne(long id) {
        ArticleVO articleVO=articleDao.getOne(id);
        if (articleVO==null || articleVO.getId()==0){
            return new ArticleBean();
        }
        return generateBean(articleVO);
    }

    @Override
    public ArticleBean voteOne(long userId, long articleId) {
        return generateBean(articleDao.voteOne(userId,articleId));
    }

    @Override
    public List<ArticleBean> voteMulti(long userId, long[] articleIds) {
        List<ArticleBean> articleBeans=new ArrayList<>();
        for (long article:articleIds){
            articleBeans.add(voteOne(userId,article));
        }
        return articleBeans;
    }

    @Override
    public List<ArticleBean> getTopN(int n) {
        return generateBeans(articleDao.getTopN(n));
    }

    @Override
    public List<ArticleBean> getLatestN(int n) {
        return generateBeans(articleDao.getLatestN(n));
    }

    @Override
    public List<ArticleBean> getTopNInTag(int n, long tagId) {
        return generateBeans(articleDao.getTopNInTag(n,tagId));
    }

    @Override
    public List<ArticleBean> getLatestNInTag(int n, long tagId) {
        return generateBeans(articleDao.getLatestNInTag(n,tagId));
    }

    @Override
    public List<ArticleBean> pageOnTime(int page, int pageSize, int direction) {
        return generateBeans(articleDao.pageOnTime(page,pageSize,direction));
    }

    @Override
    public List<ArticleBean> pageOnVotes(int page, int pageSize, int direction) {
        return generateBeans(articleDao.pageOnVotes(page,pageSize,direction));
    }

    @Override
    public List<ArticleBean> getArticlesByUser(long userId) {
        return generateBeans(articleDao.getArticlesByUser(userId));
    }

    //定义方法，代码复用
    private List<ArticleBean> generateBeans(List<ArticleVO> articleVOS){
        List<ArticleBean> articleBeans=new ArrayList<>();
        for(ArticleVO articleVO:articleVOS){
            articleBeans.add(generateBean(articleVO));
        }
        return articleBeans;
    }


    //根据ArticleVO文章基本信息，从redis数据库中查询其用户和标签，构造ArticleBean对象返回
    private ArticleBean generateBean(ArticleVO articleVO){
        long userId=articleVO.getUserId();
        UserVO userVO=userDao.getOne(userId);
        String tags=articleVO.getTags();
        String[] tagsId=tags.split(",");
        List<TagVO> tagVOS=new ArrayList<>();
        for(String s:tagsId){
            tagVOS.add(tagDao.getOneBasicInfo(Integer.parseInt(s)));
        }
        ArticleBean articleBean=new ArticleBean();
        articleBean.setArticleVO(articleVO);
        articleBean.setTags(tagVOS);
        articleBean.setUser(userVO);
        return articleBean;
    }
}
