package com.peng.redistest3.service;

import com.peng.redistest3.vo.TagVO;

import java.util.List;

public interface TagService {
    TagVO addTag(TagVO tag);
    TagVO getOneBasicInfo(long id);
    List<TagVO> getAllBasicInfo();
}
