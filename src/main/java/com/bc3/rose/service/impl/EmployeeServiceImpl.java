package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.mapper.EmployeeMapper;
import com.bc3.rose.entity.Employee;
import com.bc3.rose.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
