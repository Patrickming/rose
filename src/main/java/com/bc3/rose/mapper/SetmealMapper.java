package com.bc3.rose.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc3.rose.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    void updateStatus(int status, List<Long> ids);
}
