package com.zxb.service.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * 获取sqlSession
 * @author admin
 * @create 2020/7/1
 * @since 1.0.0
 */
@Configuration
public class SqlSessionFactoryDemo<T> {



    /**
     * 获取sqlSessionFactor对象
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean=new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(master());
        return sessionFactoryBean.getObject();
    }

    /**
     * 获取数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource master() {
        return DataSourceBuilder.create().build();
    }

    public T getMapperClass(String url) throws Exception {
        Class<?> aClass = Class.forName(url);
        SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(sqlSessionFactory());
        return (T) sqlSessionTemplate.getMapper(aClass);
    }
}
