package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.entity.ShoppingCart;
import com.bc3.rose.mapper.ShoppingCartMapper;
import com.bc3.rose.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
