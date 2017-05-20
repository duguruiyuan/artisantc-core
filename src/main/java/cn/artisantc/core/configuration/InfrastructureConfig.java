package cn.artisantc.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration(value = "InfrastructureConfig")
@EnableTransactionManagement
@PropertySource({"classpath:infrastructure.properties"})
public class InfrastructureConfig {

    @Autowired
    Environment env;

    @Autowired
    private DataSource dataSource;

    @Bean
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager());
        return transactionTemplate;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        return jpaTransactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(this.dataSource);
        em.setPersistenceUnitName("persistenceJPA");// 设置“persistence unit name”
        em.setPackagesToScan("cn.artisantc.core.persistence.entity");// 设置实体类的扫描路径
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(this.additionalProperties());
        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setShowSql(env.getProperty("jpa.showSql", Boolean.class));
        vendorAdapter.setGenerateDdl(env.getProperty("jpa.generateDdl", Boolean.class));

        return vendorAdapter;
    }

    private Map<String, Object> additionalProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }

}