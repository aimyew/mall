package com.macro.mall.base;

import com.macro.mall.MallAdminApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description base tests
 * @date 2021/11/8 10:17
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallAdminApplication.class)
@MapperScan(basePackages = "com.macro.mall.mapper")
public class BaseTests {
    @Resource
    protected HttpServletRequest request;

    String printQueryString() {
        try {
            String queryString = request.getQueryString();
            if (StringUtils.isEmpty(queryString)) {
                return "empty query string";
            }
            return queryString;
        } catch (Exception e) {
            log.info("get channel id exception : {}", e.getMessage());
        }
        return null;
    }
}
