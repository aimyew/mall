package com.macro.mall;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: TianYa
 * @date 2021/10/22 17:33
 */
public class SpiderTest {
    public static void main(String[] args) {
        int pageStart = 1;
        int pageEnd = 2;
        long l = System.currentTimeMillis();
        // use single thread crawl a web page code instead of multiple threads can avoid the service block 服务提供者的网络阻塞
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = pageStart; i < pageEnd; i++) {
            int finalI = i;
            executorService.execute(() -> {
                spiderOnePage(finalI);
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        while (executorService.isShutdown()) {
            if(executorService.isTerminated()){
                System.out.println("所有的子线程都结束了！");
                System.out.println(System.currentTimeMillis() - l);
                break;
            }
        }

    }

    private static void spiderOnePage(Integer pageNum) {
        String url = "https://www.baidu.com";
        String getResult = HttpUtil.createGet(url)
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
        //document.select("div[class=ft]").remove();

        // 移除用户的超链
        document.select("a[class=info]").removeAttr("href").attr("href", "#");

        //document.select("div[class=item item-ht]").select("div[class=bd]").text();
        // contains("楼主")
        List<Node> floorNodes = document.select("div[class=content]").get(0).childNodes();
        List<Node> floorNodesHandled = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        boolean flag = false;
        for (Node floorNode : floorNodes) {
            Attributes attributes = floorNode.attributes();

            if (floorNode.attributes().hasKey("class") && floorNode.attributes().get("class").equals("item item-ht item-lz")) {
                System.out.println(String.format("check lz floorSeq: %s author: %s ...", floorNode.attributes().get("data-id"), floorNode.attributes().get("data-user")));
                //continue;
            }
            if (floorNode.attributes().hasKey("class") && floorNode.attributes().get("class").equals("item item-ht ")) {
                boolean hasLz = false;
                for (Node childNode : floorNode.childNodes()) {
                    if (childNode.childNodes().size() > 0) {
                        if (childNode.toString().contains("u-badge\">楼主")) {
                            hasLz = true;
                            break;
                        }
                    }
                }
                System.out.println(String.format("check normal(contaion lz %s) floorSeq: %s author: %s ...", hasLz, floorNode.attributes().get("data-id"), floorNode.attributes().get("data-user")));
                if (!hasLz) {
                    flag = true;
                    set.add(Integer.parseInt(floorNode.attributes().get("data-id")));
                }
            }
            if (flag) {
                floorNodesHandled.remove(floorNodesHandled.size() - 1);
                flag = false;
            } else {
                floorNodesHandled.add(floorNode);
            }
        }


        // 原来页面有 41个
        floorNodes = floorNodes;
        System.out.println(set);
        for (Integer integer : set) {
            document.select("div[data-id=" + integer + "]").remove();
        }

        // 获取内容主体
        //System.out.println(document.html());

        // 输出文件
        FileWriter output = null;
        BufferedWriter writer = null;
        try {
            output = new FileWriter("D:\\IdeaProjects155\\deepmind\\deepmind-backend\\deep-service\\src\\test\\java\\org\\example\\deep\\ty\\html\\handle\\788155-" + pageNum + ".html");
            writer = new BufferedWriter(output);
            writer.write(document.html());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
                if (null != output) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
