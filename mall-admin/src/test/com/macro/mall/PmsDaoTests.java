package com.macro.mall;


import cn.hutool.json.JSONUtil;
import com.macro.mall.dao.PmsMemberPriceDao;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.service.UmsAdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmsDaoTests {
    @Resource
    private PmsMemberPriceDao memberPriceDao;
    @Resource
    private PmsProductDao productDao;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsDaoTests.class);

    @Test
    @Transactional
    @Rollback
    public void testInsertBatch() {
        List<PmsMemberPrice> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PmsMemberPrice memberPrice = new PmsMemberPrice();
            memberPrice.setProductId(1L);
            memberPrice.setMemberLevelId((long) (i + 1));
            memberPrice.setMemberPrice(new BigDecimal("22"));
            list.add(memberPrice);
        }
        int count = memberPriceDao.insertList(list);
        Assert.assertEquals(5, count);
    }

    @Test
    public void testGetProductUpdateInfo() {
        PmsProductResult productResult = productDao.getUpdateInfo(7L);
        String json = JSONUtil.parse(productResult).toString();
        LOGGER.info(json);
    }

    @Test
    public void testLogin() {
        UserDetails userDetails = umsAdminService.loadUserByUsername("admin");
        boolean matches = passwordEncoder.matches("macro123", userDetails.getPassword());
        if (!matches) {
            LOGGER.info("密码不正确");
        } else {
            LOGGER.info("密码正确");
        }
    }
}
