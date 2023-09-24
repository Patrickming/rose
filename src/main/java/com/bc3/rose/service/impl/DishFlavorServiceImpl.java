package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.mapper.DishFlavorMapper;
import com.bc3.rose.entity.DishFlavor;
import com.bc3.rose.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
