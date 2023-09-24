package com.bc3.rose.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc3.rose.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    void updateStatus(int status, List<Long> ids);
}
