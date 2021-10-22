package com.macro.mall;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @description: TianYa
 * @date 2021/10/22 17:33
 */
public class TianYaTest {
    public static void main(String[] args) {
        String url = "https://bbs.tianya.cn/m/post-house-252774-1.shtml";// house
        String urlTest = "https://bbs.tianya.cn/m/post-house-252775-1.shtml";// test
        String getResult = HttpUtil.createGet(urlTest)
                .execute()
                //返回参数格式utf-8
                .charset("utf-8")
                .body();
        Document document = Jsoup.parse(getResult);
        Elements title = document.getElementsByClass("title");
        for (Element element : title) {
            System.out.println(element.text());
        }


        Elements module = document.getElementsByClass("block fc-blue");
        System.out.println("module name = " + module.text());
    }
}
