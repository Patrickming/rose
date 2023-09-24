package com.bc3.rose.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc3.rose.entity.AddressBook;
import com.bc3.rose.mapper.AddressBookMapper;
import com.bc3.rose.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
