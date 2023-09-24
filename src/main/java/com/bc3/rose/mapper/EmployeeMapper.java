package com.bc3.rose.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc3.rose.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
