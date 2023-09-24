package com.bc3.rose.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc3.rose.dto.DishDto;
import com.bc3.rose.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表: dish、dish_flavor
    public void saveWithFlavor (DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    //删除菜品信息
    public void deleteByIds(String ids);

    //修改菜品状态
    public void updateStatus(int status,String ids);
}
