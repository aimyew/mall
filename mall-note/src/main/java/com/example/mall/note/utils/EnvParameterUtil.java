package com.example.mall.note.utils;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @description get any specified parameter from application context
 * @date 2021/12/22 10:15
 */
@Component
public class EnvParameterUtil implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    public String getServerPort() {
        String serverPort = environment.getProperty("server.port");
        return String.format("server port is %s", serverPort);
    }
}
