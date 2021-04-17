package com.peng.redistest3.web;


import com.peng.redistest3.service.UserService;
import com.peng.redistest3.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理
 * 添加用户、查看一个用户、查看所有用户
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public UserVO add(UserVO userVO){
        return userService.addUser(userVO);
    }
    @GetMapping("/{id}")
    public UserVO getOne(@PathVariable long id){
        return userService.getOne(id);
    }
    @GetMapping("/getAll")
    public List<UserVO> getAll(){
        return userService.getAll();
    }

}
