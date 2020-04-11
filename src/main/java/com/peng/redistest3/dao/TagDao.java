package com.peng.redistest3.dao;

import com.peng.redistest3.vo.TagVO;

import java.util.List;

public interface TagDao {
    TagVO addTag(TagVO tag);
    TagVO getOneBasicInfo(long id);
    List<TagVO> getAllBasicInfo();
}
