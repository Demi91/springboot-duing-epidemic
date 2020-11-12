package com.duing.service;

import com.duing.DataHandler;
import com.duing.bean.DataBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public List<DataBean> list() {
        return DataHandler.getData();
    }
}
