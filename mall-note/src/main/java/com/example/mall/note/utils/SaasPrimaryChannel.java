package com.example.mall.note.utils;

public class SaasPrimaryChannel {

    // 创建线程局部变量，并初始化值
    private static ThreadLocal<String> memberIdThreadLocal = new ThreadLocal<String>() {
        protected String initialValue() {
            return "primary";
        };
    };

    // 提供线程局部变量set方法
    public static void setPrimaryChannelId(String channelId) {
        memberIdThreadLocal.set(channelId);
    }

    // 提供线程局部变量get方法
    public static String getPrimaryChannelId() {
        return memberIdThreadLocal.get();
    }
}
