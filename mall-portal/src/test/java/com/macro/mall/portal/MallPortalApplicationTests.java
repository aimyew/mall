package com.macro.mall.portal;

import cn.hutool.json.JSONUtil;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.MemberReadHistory;
import com.macro.mall.portal.repository.MemberReadHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallPortalApplicationTests {
    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;

    @Test
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void contextLoads() {
        UmsMember member = new UmsMember();

        // memberReadHistoryRepository.save(memberReadHistory);
        MemberReadHistory memberReadHistory = memberReadHistoryRepository.findAll().get(0);
        System.out.println("000 " + JSONUtil.parse(memberReadHistory));


        try {
            // MemberReadHistory insert = memberReadHistoryRepository.insert(memberReadHistory);
            MemberReadHistory find = memberReadHistoryRepository.findByMemberNickname("pony");
            System.out.println("111 " + JSONUtil.parse(find));
            throw new Exception("a");
        } catch (Exception e) {
            System.out.println("ee  ee");
        } finally {
            memberReadHistory.setId(null);
            memberReadHistory.setMemberId(666l);
            MemberReadHistory insert = memberReadHistoryRepository.insert(memberReadHistory);
            System.out.println("222 " + JSONUtil.parse(insert));
        }


    }

}
