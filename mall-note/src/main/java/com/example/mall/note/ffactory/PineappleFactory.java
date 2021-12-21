package com.example.mall.note.ffactory;

import com.example.mall.note.domain.Fruit;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description pineapple factory
 * @date 2021/12/20 15:39
 */
@Configuration
@Component(value = "pineappleFactory1")
public class PineappleFactory implements FruitFactory {

    @Override
    public void setManufacturedDate(Fruit fruit) {
        fruit.setProductionDate(new Date());
        System.out.println("set pineapple manufactured date ...");
    }
}
