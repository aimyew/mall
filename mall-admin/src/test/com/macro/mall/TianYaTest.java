package com.macro.mall;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: TianYa
 * @date 2021/10/22 17:33
 */
public class TianYaTest {
    public static void main(String[] args) {
        String url = "https://bbs.tianya.cn/m/post-house-252774-1.shtml";// house
        String urlTest = "https://bbs.tianya.cn/m/post-house-788155-1.shtml";// test
        String getResult = HttpUtil.createGet(urlTest)
                .execute()
                //返回参数格式utf-8
                .charset("utf-8")
                .body();
        Document document = Jsoup.parse(getResult);
//        Elements title = document.getElementsByClass("title");
//        System.out.println(title.text());
//        Elements module = document.getElementsByClass("block fc-blue");
//        System.out.println("module name = " + module.text());

        // 移除每层的 footer 元素 埋红包 点赞 评论 只看TA
        document.select("div[class=ft]").remove();
        // 移除用户的超链
        document.select("a[class=info]").removeAttr("href").attr("href", "#");

        document.select("div[class=item item-ht]").select("div[class=bd]").text();
        // contains("楼主")
        List<Node> floorNodes = document.select("div[class=content]").get(0).childNodes();

        for (Node floorNode : floorNodes) {


            Attributes attributes = floorNode.attributes();

            if (floorNode.attributes().hasKey("class") && floorNode.attributes().get("class").equals("item item-ht item-lz")) {
                System.out.println(String.format("check lz floorSeq: %s author: %s ...", floorNode.attributes().get("data-id"), floorNode.attributes().get("data-user")));
                continue;
            }
            if (floorNode.attributes().hasKey("class") && floorNode.attributes().get("class").equals("item item-ht ")) {
                boolean hasLz = false;
                for (Node childNode : floorNode.childNodes()) {
                    if (childNode.childNodes().size() > 0) {
                        if (childNode.toString().contains("楼主")) {
                            hasLz = true;
                            break;
                        }
                    }
                }
                if (!hasLz) {
                    // floorNode.childNodes().clear();
                    floorNode = floorNode;
                    floorNode = new Element(Tag.valueOf("div"), "", floorNode.attributes());
                }
                System.out.println(String.format("check normal(contaion lz %s) floorSeq: %s author: %s ...", hasLz, floorNode.attributes().get("data-id"), floorNode.attributes().get("data-user")));
            }
        }






        // 原来页面有 41个
        floorNodes = floorNodes;

        Element element = document.select("div[class=content]").get(0);


//        for (int i = 0; i < floorChildNodesWithLz.size(); i++) {
//            element.childNodes().set(i, floorChildNodesWithLz.get(i));
//        }

        List<Node> nodesA = document.select("div[class=content]").get(0).childNodes();
        // 获取内容主体
        System.out.println(document.html());
    }
}
