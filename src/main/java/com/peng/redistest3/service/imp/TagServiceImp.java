package com.peng.redistest3.service.imp;

import com.peng.redistest3.dao.TagDao;
import com.peng.redistest3.service.TagService;
import com.peng.redistest3.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImp implements TagService {
    @Autowired
    private TagDao tagDao;

    @Override
    public TagVO addTag(TagVO tag) {
        return tagDao.addTag(tag);
    }

    @Override
    public TagVO getOneBasicInfo(long id) {
        TagVO tagVo=tagDao.getOneBasicInfo(id);
        return tagVo==null?new TagVO():tagVo;
    }

    @Override
    public List<TagVO> getAllBasicInfo() {
        return tagDao.getAllBasicInfo();
    }
}
