package com.example.mall.note;

import com.example.mall.note.utils.EnvParameterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.UnaryOperator;

//@SpringBootTest
class MallNoteApplicationTests {

    @Test
    void contextLoads() {
        printServerPort(false);
        printRandomNum(false);
    }

    private static final Integer KEEP_ALIVE_TIME = 2;

    @Test
    void commonTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = math(list, x -> x * 2);
        System.out.println(result); // [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
    }

    public static <T> List<T> math(List<T> list, UnaryOperator<T> uo) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            result.add(uo.apply(t));
        }
        return result;
    }
    public static <T> List<T> math(List<T> list, UnaryOperator<T> uo, UnaryOperator<T> uo2) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            result.add(uo.andThen(uo2).apply(t));
        }
        return result;
    }

    /**
     * 输出当前服务的端口号
     */
    @Resource
    private EnvParameterUtil envParameterUtil;

    private void printServerPort(boolean b) {
        if (shouldOrNotExecute(b)) return;
        String serverPort = Optional.ofNullable(envParameterUtil.getServerPort()).orElse("0");
        System.out.println(serverPort);
    }

    /**
     * 输出随机数
     */
    public final static short MAGIC = 0xf;

    private void printRandomNum(boolean b) {
        if (shouldOrNotExecute(b)) return;
        Random random = new Random();
        int i1 = random.nextInt(5);
        System.out.println(i1);
        System.out.println(MAGIC);
    }

    private boolean shouldOrNotExecute(boolean b) {
        return !b;
    }
}
