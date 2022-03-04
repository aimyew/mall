package com.example.mall.note.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@MapperScan(basePackages = {"com.lvmama.messaging.**.dao"})
public class DynamicDataSourceConfig {

    @Bean("primary")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primary() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "shanxi")
    @ConfigurationProperties(prefix = "spring.datasource.shanxi")
    public DataSource shanxi() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "yixian")
    @ConfigurationProperties(prefix = "spring.datasource.yixian")
    public DataSource yixian() {
        return DataSourceBuilder.create().build();
    }

    @Bean("hubei")
    @ConfigurationProperties(prefix = "spring.datasource.hubei")
    public DataSource hubei() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "xiyu")
    @ConfigurationProperties(prefix = "spring.datasource.xiyu")
    public DataSource xiyu() {
        return DataSourceBuilder.create().build();
    }


    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(16);
        dataSourceMap.put("primary", primary());
        dataSourceMap.put("shanxi", shanxi());
        dataSourceMap.put("yixian", yixian());
        dataSourceMap.put("hubei", hubei());
        dataSourceMap.put("xiyu", xiyu());
        dynamicDataSource.setDefaultDataSource(primary());
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource());
        sessionFactory.setTypeAliasesPackage("com.lvmama.messaging.core.bean.**");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/dao/*.xml"));
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}