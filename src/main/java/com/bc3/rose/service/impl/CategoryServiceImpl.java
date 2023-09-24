package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.mapper.CategoryMapper;
import com.bc3.rose.common.CustomException;
import com.bc3.rose.entity.Category;
import com.bc3.rose.entity.Dish;
import com.bc3.rose.entity.Setmeal;
import com.bc3.rose.service.CategoryService;
import com.bc3.rose.service.DishService;
import com.bc3.rose.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper();
        //添加查询条件根据分类id进行查询
        dishQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishQueryWrapper);

        //判断当前分类是否关联菜品，如果已经关联，抛出一个异常
        if (count1 > 0){
            //如果已经关联，抛出一个异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count();

        //判断当前分类是否关联套餐，如果已经关联，抛出一个异常
        if (count2 > 0 ){
            //如果已经关联，抛出一个异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        //正常删除分类
        super.removeById(id);
    }
}
