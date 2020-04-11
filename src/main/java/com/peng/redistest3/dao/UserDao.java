package com.peng.redistest3.dao;

import com.peng.redistest3.vo.UserVO;

import java.util.List;

public interface UserDao {
    UserVO addUser(UserVO userVO);
    UserVO getOne(long id);
    List<UserVO> getAll();
}
