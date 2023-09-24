package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.entity.User;
import com.bc3.rose.mapper.UserMapper;
import com.bc3.rose.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
