package com.pulsara.fse.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

//import javax.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManagerFactory;

import java.beans.BeanProperty;

import javax.sql.DataSource;

@Configuration
@EntityScan(basePackages = "com.pulsara.fse.models")
@EnableJpaRepositories(basePackages = "com.pulsara.fse.repository")
public class DatabaseConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.pulsara.fse.models");
        // Set JpaVendorAdapter with the desired persistence provider
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        //emf.setPersistenceProviderClass("org.hibernate.jpa.HibernatePersistenceProvider");
        return emf;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        
        // Set the persistence provider property
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");

        // Additional properties can be set here, if needed

        return vendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
