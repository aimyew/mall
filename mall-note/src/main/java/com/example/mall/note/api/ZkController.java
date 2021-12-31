package com.example.mall.note.api;

import com.example.mall.note.config.ZkApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <dl>
 * <dt><p>@description zk controller <p>@date 2021/12/29 10:57</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/zk")
public class ZkController {
    @Resource
    private ZkApi zkApi;

    @GetMapping(value = {"/test/{crud}/{nodePath}", "/test/{crud}/{nodePath}/{nodeData}"})
    public String test(@PathVariable String crud, @PathVariable String nodePath, @PathVariable(required = false) String nodeData) {
        String res;
        nodePath = "/" + nodePath;
        switch (crud) {
            case "zkR":
                res = zkR(Optional.ofNullable(zkApi.getData(nodePath, null)).orElse(null), Objects::nonNull, System.out::println, "query: " + nodePath + ", res: ", "query: " + nodePath + " > null");
                break;
            case "zkC":
                res = zkCud(zkApi.createNode(nodePath, nodeData), Boolean.TRUE::equals, System.out::println, "create success", "create fail");
                break;
            case "zkU":
                res = zkCud(zkApi.updateNode(nodePath, nodeData), Boolean.TRUE::equals, System.out::println, "update success", "update fail");
                break;
            case "zkD":
                res = zkCud(zkApi.deleteNode(nodePath), Boolean.TRUE::equals, System.out::println, "delete success", "delete fail");
                break;
            default:
                res = "order error";
                break;
        }
        return res;
    }

    public String zkCud(Boolean boo, Predicate<Boolean> predicate, Consumer<String> consumer, String msg1, String msg2) {
        if (predicate.test(boo)) {
            consumer.accept(msg1);
            return msg1;
        } else {
            consumer.accept(msg2);
            return msg2;
        }
    }

    public String zkR(String res, Predicate<String> predicate, Consumer<String> consumer, String msg1, String msg2) {
        if (predicate.test(res)) {
            consumer.accept(msg1 + res);
            return msg1 + res;
        } else {
            consumer.accept(msg2);
            return msg2;
        }
    }
}