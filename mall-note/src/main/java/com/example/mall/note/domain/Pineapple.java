package com.example.mall.note.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description pineapple
 * @date 2021/12/20 15:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pineapple extends Fruit implements Serializable {
    private Boolean isTropical;
}
