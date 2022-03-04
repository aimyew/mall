package com.example.mall.note.utils;

/**
 * @date 2022/3/2 10:09
 * @apiNote EmojiUtil
 */
public class EmojiUtil {

    public void change() {
        String txt = "测试666\uD83D\uDC2F";// 测试666🐯
        if (cn.hutool.extra.emoji.EmojiUtil.containsEmoji(txt)) {
            // 11  测试666:tiger:
            System.out.println("11  " + cn.hutool.extra.emoji.EmojiUtil.toAlias(txt));
        }
        // 22 测试666🐯
        System.out.println("22 " + cn.hutool.extra.emoji.EmojiUtil.toUnicode(txt));
    }
}
