package com.bc3.rose.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc3.rose.common.BaseContext;
import com.bc3.rose.common.R;
import com.bc3.rose.dto.OrdersDto;
import com.bc3.rose.entity.OrderDetail;
import com.bc3.rose.entity.Orders;
import com.bc3.rose.entity.User;
import com.bc3.rose.service.OrderDetailService;
import com.bc3.rose.service.OrderService;
import com.bc3.rose.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);

        orderService.sumbit(orders);
        return R.success("下单成功");
    }

    /**
     * 管理员显示订单信息
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> getOrders(int page,int pageSize,Long number,String beginTime,String endTime){
        log.info("page: {},pageSize: {},number: {},beginTime: {},endTime: {}",page,pageSize,number,beginTime,endTime);
        //页面构造器
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> dtoPage = new Page<>();

        //查询所有orders表信息
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //查询number
        if (number != null){
            queryWrapper.like(Orders::getNumber, number);
        }
        //查询beginTime 大于等于这个时间
        if (beginTime != null){
            queryWrapper.ge(Orders::getOrderTime, beginTime);
        }
        //查询endTime 小于等于这个时间
        if (endTime != null){
            queryWrapper.le(Orders::getOrderTime, endTime);
        }
        //条件排序
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行查询
        orderService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> list = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();

            //对象拷贝
            BeanUtils.copyProperties(item, ordersDto);
            //获取用户id
            Long userId = item.getUserId();
            //根据用户id查询用户对象
            User user = userService.getById(userId);
            //获取用户姓名
            if (user != null) {
                String userName = user.getName();
                ordersDto.setUserName(userName);
            }

            return ordersDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize){
        log.info("page = {},pageSize = {}",page,pageSize);

        //构造分页构造器
        Page<Orders> pageInfo = new Page(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getCheckoutTime);

        //执行查询
        orderService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");

        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> list = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();

            BeanUtils.copyProperties(item,ordersDto);

            Long orderid = item.getId();//订单号

            //根据订单号查询订单详情
            //构造条件构造器
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //添加过滤条件
            lambdaQueryWrapper.eq(OrderDetail::getOrderId, orderid);
            //执行查询
            List<OrderDetail> orderDetailList = orderDetailService.list(lambdaQueryWrapper);

            ordersDto.setOrderDetails(orderDetailList);
            ordersDto.setSumNum(orderDetailList.size());

            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list);

        return R.success(ordersDtoPage);
    }

    /**
     * 派送订单
     * @param map
     * @return
     */
    @PutMapping()
    public R<String> updateStatus(@RequestBody Map<String,String> map){

        int status = Integer.parseInt(map.get("status"));

        String id = map.get("id");

        Orders orders = orderService.getById(id);

        orders.setStatus(status);

        orderService.updateById(orders);

        return R.success("订单派送成功");
    }

}
