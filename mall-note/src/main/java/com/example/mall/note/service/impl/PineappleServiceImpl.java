package com.example.mall.note.service.impl;

import com.example.mall.note.service.PineappleService;
import org.springframework.stereotype.Service;

/**
 * @description pineapple service impl
 * @date 2021/12/20 15:04
 */
@Service
public class PineappleServiceImpl implements PineappleService {

    @Override
    public void print(String name) {
        System.out.println(name);
    }
}
