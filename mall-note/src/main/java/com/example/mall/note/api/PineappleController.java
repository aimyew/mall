package com.example.mall.note.api;

import com.example.mall.note.ffactory.FruitFactory;
import com.example.mall.note.ffactory.PineappleFactory;
import com.example.mall.note.domain.Pineapple;
import com.example.mall.note.service.PineappleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description pineapple controller
 * @date 2021/12/20 14:57
 */
@RestController
@RequestMapping(value = "/pineapple")
public class PineappleController {

    /**
     * JSR(Java Specification Requests) Java 规范提案
     * JSR 250 规范包含 用于将资源注入到端点实现类的注释 和 用于管理应用程序生命周期的注释 (分别是 @Resource @PostConstruct @PreDestroy)
     */

    /**
     * @Autowired 默认 按 byType 自动注入
     */
    @Autowired
    private PineappleService pineappleService;

    /**
     * @Resource 默认 按 byName自动注入
     * 其装配顺序为
     * 1.如果同时指定了 name 和 type 则从 Spring 上下文中找到唯一匹配的 bean 进行装配 若找不到则抛出异常
     * 2.如果指定了 name 则从上下文中查找名称（id）匹配的 bean 进行装配 若找不到则抛出异常
     * 3.如果指定了 type 则从上下文中找到类型匹配的唯一 bean 进行装配 若找不到或者找到多个则会抛出异常
     * 4.如果既没有指定 name 又没有指定 type 则自动按照 byName 方式进行装配（见2） 如果没有匹配 则回退为一个原始类型（InjectionDAO）进行匹配 如果匹配则自动装配；
     */
    @Resource(name = "pineappleFactory1")
    private PineappleFactory pineappleFactory1;


    @Autowired
    @Qualifier("pineappleFactory1")
    private FruitFactory fruitFactory;

    @PostMapping(value = "print")
    public String aa() {
        pineappleService.print("pineapple");

        Pineapple pineapple1 = new Pineapple();
        pineapple1.setName("pineapple");
        pineapple1.setIsTropical(true);

        Pineapple pineapple2 = new Pineapple();
        pineapple2.setName("pp");
        pineapple2.setIsTropical(true);

        System.out.println(pineapple1.equals(pineapple2));

        return "pineapple";
    }
}
