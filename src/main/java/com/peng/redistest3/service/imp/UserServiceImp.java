package com.peng.redistest3.service.imp;

import com.peng.redistest3.dao.UserDao;
import com.peng.redistest3.service.UserService;
import com.peng.redistest3.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserVO addUser(UserVO userVO) {
        return userDao.addUser(userVO);
    }

    @Override
    public UserVO getOne(long id) {
        UserVO userVO=userDao.getOne(id);
        return userVO==null?new UserVO():userVO;
    }

    @Override
    public List<UserVO> getAll() {
        return userDao.getAll();
    }
}
