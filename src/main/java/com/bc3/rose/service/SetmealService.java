package com.bc3.rose.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc3.rose.dto.SetmealDto;
import com.bc3.rose.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要需要删除套餐和菜品的关联数据
     *
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

    /**
     * 修改套餐状态
     * @param status
     * @param ids
     */
    public void updateStatus(int status, List<Long> ids);

    /**
     * 根据id查询套餐信息和对应菜品信息
     */
    public SetmealDto getByIdWithDish(Long id);

    /**
     * 更新套餐信息，同时更新对应的菜品信息
     * @param setmealDto
     */
    public void updateWithDish(SetmealDto setmealDto);
}
