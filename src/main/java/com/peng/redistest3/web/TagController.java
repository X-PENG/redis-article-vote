package com.peng.redistest3.web;

import com.peng.redistest3.service.TagService;
import com.peng.redistest3.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/add")
    public TagVO add(TagVO tag){
        return tagService.addTag(tag);
    }
    @GetMapping("/basic/{id}")
    public TagVO getOne(@PathVariable long id){
        return tagService.getOneBasicInfo(id);
    }
    @GetMapping("/basic/getAll")
    public List<TagVO> getAll(){
        return tagService.getAllBasicInfo();
    }
}
