package com.dl.springboot.readwrite.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:luiz
 * @Date: 2018/3/21 16:46
 * @Descripton:
 * @Modify :
 **/
@Configuration
@Slf4j
@MapperScan(basePackages = "com.dl.springboot.readwrite.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration  {
    private static Logger LOGGER = LoggerFactory.getLogger(MybatisConfiguration.class);

    static final String MAPPER_LOCATION = "classpath*:/mapping/*.xml";

    //默认去找application.yum中的druid.type相当于将配置文件中的该值赋值给dataSourceType
    @Value("${druid.type}")
    private Class<? extends DataSource> dataSourceType;


    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "druid.master") //意思是从application.yum中找druid.master开头所有的信息都要放到要创建的masterDataSource并且交给spring管理
    public DataSource masterDataSource() throws Exception{
        DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        LOGGER.info("========MASTER: {}=========", masterDataSource);
        return masterDataSource;
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "druid.slave")
    public DataSource slaveDataSource()throws Exception{
        DataSource slaveDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        LOGGER.info("========SLAVE: {}=========", slaveDataSource);
        return slaveDataSource;
    }



   /* //druid监控界面需要用的到servlet
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", "localhost");
        reg.addInitParameter("deny","/deny");
        LOGGER.info(" druid console manager init : {} ", reg);
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico, /druid/*");
        LOGGER.info(" druid filter register : {} ", filterRegistrationBean);
        return filterRegistrationBean;
    }*/

    /**
     * 1、创建动态数据源
     * @throws Exception
     * @Primary该注解表示在同一个接口有多个类可以注入的时候，默认选择哪个，而不是让@Autowired报错
     */
    @Bean(name="dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource){
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataBaseContextHolder.DataBaseType.MASTER, masterDataSource);
        targetDataSource.put(DataBaseContextHolder.DataBaseType.SLAVE, slaveDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;
    }

    /**
     * 2、根据数据源创建SqlSessionFactory
     * @throws Exception
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource")DynamicDataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean("transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier("dynamicDataSource")DynamicDataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }





}
