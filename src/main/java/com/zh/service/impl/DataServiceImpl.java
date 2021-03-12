package com.zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.bean.DataBean;
import com.zh.mapper.DataMapper;
import com.zh.service.DataService;
import org.springframework.stereotype.Service;



@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, DataBean> implements DataService{

}
