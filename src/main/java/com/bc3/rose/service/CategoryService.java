package com.bc3.rose.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc3.rose.entity.Category;

public interface CategoryService extends IService<Category> {

    /**
     * 自定义根据id删除分类信息
     * @param id
     */
    public void remove(Long id);
}
