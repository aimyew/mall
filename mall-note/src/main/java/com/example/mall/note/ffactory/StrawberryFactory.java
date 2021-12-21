package com.example.mall.note.ffactory;

import com.example.mall.note.domain.Fruit;

import java.util.Date;

/**
 * @description strawberry factory
 * @date 2021/12/20 16:12
 */
public class StrawberryFactory implements FruitFactory {
    @Override
    public void setManufacturedDate(Fruit fruit) {
        fruit.setProductionDate(new Date());
        System.out.println("set strawberry manufactured date ...");
    }
}
