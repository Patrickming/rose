package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.mapper.DishMapper;
import com.bc3.rose.dto.DishDto;
import com.bc3.rose.entity.Dish;
import com.bc3.rose.entity.DishFlavor;
import com.bc3.rose.service.DishFlavorService;
import com.bc3.rose.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应口味
     *
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到dish表
        this.save(dishDto);

        //获取菜品Id
        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }

        //保存菜品口味数据到dish_flavor表
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {

        DishDto dishDto = new DishDto();

        //查询菜品基本信息，从dish表中查询
        Dish dish = this.getById(id);
        //数据拷贝
        BeanUtils.copyProperties(dish, dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表中查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());

        List<DishFlavor> dishFlavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(dishFlavors);

        return dishDto;
    }

    /**
     * 更新菜品信息，同时更新对应的口味信息
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应的口味信息---dish_flavor的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }


    /**
     * 删除菜品信息
     * @param ids
     */
    @Override
    @Transactional
    public void deleteByIds(String ids) {
        log.info("删除菜品，id为：{}",ids);

        //判断是否是批量删除
        if (ids.contains(",")){
            String[] split = ids.split(",");
            List<Long> list = new ArrayList<>();
            for (String s1 : split) {
                list.add(Long.valueOf(s1));
            }
            this.removeByIds(list);
        }else {
            this.removeById(ids);
        }


    }

    /**
     * 修改菜品状态
     * @param ids
     */
    @Override
    public void updateStatus(int status,String ids) {

            String[] split = ids.split(",");
            List<Long> list = new ArrayList<>();
            for (String s1 : split) {
                list.add(Long.valueOf(s1));
            }

            baseMapper.updateStatus(status,list);
    }


}
