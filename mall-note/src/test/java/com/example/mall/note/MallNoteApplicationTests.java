package com.example.mall.note;

import com.example.mall.note.utils.EnvParameterUtil;
import org.junit.jupiter.api.Test;

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
        // List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // List<Integer> result = math(list, x -> x * 2);
        // System.out.println(result); // [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
        // Input: s = "PAY P ALI S HIR I NG", numRows = 3
        // Output: "PAHNAPLSIIGYIR"
        // Input: s = "PAYP AL ISHI RING", numRows = 4
        // Output: "PINALSIGYAHRPI"
        // System.out.println(convert("PAYPALISHIRING", 4));

    }

    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }
        int curRow = 0;
        boolean goingDown = false;
        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) {
                goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1;
        }
        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) {
            ret.append(row);
        }
        return ret.toString();
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
