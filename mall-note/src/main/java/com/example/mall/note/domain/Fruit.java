package com.example.mall.note.domain;

import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @description abstract fruit
 * @date 2021/12/20 15:49
 */
public abstract class Fruit {
    private String name;
    private Date productionDate;

    public void doPlant(String name) {
        if (StringUtils.hasLength(name)) {
            this.name = name;
        }
        System.out.println(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
}
