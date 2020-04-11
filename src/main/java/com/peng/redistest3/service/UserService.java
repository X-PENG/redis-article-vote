package com.peng.redistest3.service;

import com.peng.redistest3.vo.UserVO;

import java.util.List;

public interface UserService {
    UserVO addUser(UserVO userVO);
    UserVO getOne(long id);
    List<UserVO> getAll();
}
