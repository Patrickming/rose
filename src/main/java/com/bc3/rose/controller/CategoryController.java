package com.bc3.rose.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc3.rose.common.R;
import com.bc3.rose.entity.Category;
import com.bc3.rose.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("新增菜品的信息：{}",category);
        categoryService.save(category);

        return R.success("新增分类成功");
    }

    /**
     * 分类信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<IPage> page(int page, int pageSize){
        log.info("page={}，pageSize={}，name={}",page,pageSize);

        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort);

        //执行查询
      // categoryService.page(pageInfo, queryWrapper);
        return R.success(categoryService.page(pageInfo,queryWrapper));
    }

    @DeleteMapping()
    public R<String> delete(Long ids){
        log.info("删除分类，id为：{}",ids);

//        categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类：{}",category);

        categoryService.updateById(category);
        return R.success("分类信息修改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //根据条件查询
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getType).orderByDesc(Category::getCreateTime);

        //调用方法查询
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
