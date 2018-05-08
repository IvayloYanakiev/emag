package com.emag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfig {


    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/emag";
    private final String JDBC_USERNAME = "root";
    private final String JDBC_PASS = "1234";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASS);

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager txManager() {

        return new DataSourceTransactionManager(dataSource());
    }
}
