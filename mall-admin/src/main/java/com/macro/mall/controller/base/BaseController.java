package com.macro.mall.controller.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.macro.mall.common.constant.Constants.HOST;

/**
 * @description: base controller
 * @date 2021/10/29 11:38
 */
@Slf4j
public class BaseController {

    @Resource
    protected HttpServletRequest request;

    Integer getChannelId() {
        try {
            String primaryChannelId = request.getHeader(HOST);
            if (StringUtils.isEmpty(primaryChannelId)) {
                return null;
            }
            return Integer.parseInt(primaryChannelId);
        }catch (Exception e) {
            log.info("get channel id exception : {}", e.getMessage());
        }
        return null;
    }

    Integer getUserId() {
        try {
            String customerInfoId = request.getHeader(null);
            if (StringUtils.isEmpty(customerInfoId)) {
                return null;
            }
            return Integer.parseInt(customerInfoId);
        }catch (Exception e) {
            log.info("get user id exception : {}", e.getMessage());
        }
        return null;
    }
}
