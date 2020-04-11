package com.peng.redistest3.web;


import com.alibaba.fastjson.JSON;
import com.peng.redistest3.bean.ArticleBean;
import com.peng.redistest3.vo.ArticleVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/t1")
    public ArticleBean t1(){
        ArticleBean articleBean=new ArticleBean();
        articleBean.setArticleVO(new ArticleVO());
        return articleBean;
    }
    @GetMapping("/t2")
    public String t2(){
        ArticleBean articleBean=new ArticleBean();
        articleBean.setArticleVO(new ArticleVO());
        return JSON.toJSONString(articleBean);
    }
    @GetMapping("/t3")
    public String t3(long[] aid){
        StringBuffer stringBuffer=new StringBuffer();
        System.out.println("size="+aid.length);
        stringBuffer.append("[");
        for (long id:aid){
            stringBuffer.append(id+" ");
            System.out.println(id);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
