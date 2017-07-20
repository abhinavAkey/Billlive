package com.beatus.billlive.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Persistence configuration using JPA and Hibernate as the JPA provider.
 * Spring's {@link PlatformTransactionmanager} is used as the transaction
 * manager. XA (distributed) transactions using a two-phase commit across
 * multiple datasources is not available in this configuration.
 *
 * @author Abhinav Akey
 * @Since 1.0
 */
@Configuration
@PropertySource(
        name = "applicationProperties",
        value = "classpath:application.properties", 
        ignoreResourceNotFound = false) 
@EnableTransactionManagement
public class JpaConfiguration {

    @Value("${persistence.jpa.datasource.dialect}")
    private String databaseDialect;

    @Value("${persistence.jpa.vendor.hibernate.show_sql:true}")
    private String showSql;

    @Value("${persistence.jpa.vendor.hibernate.format_sql:true}")
    private String formatSql;

    @Value("${persistence.jpa.vendor.hibernate.use_sql_comments:true}")
    private String useSqlComments;

    @Profile("!test")
    public static class DefaultConfiguration {

        @Value("${persistence.datasource.driverClass}")
        private String databaseDriverClass;

        @Value("${persistence.datasource.connectionUrl}")
        private String databaseConnectionUrl;

        @Value("${persistence.datasource.username}")
        private String databaseUserName;

        @Value("${persistence.datasource.password}")
        private String databasePassword;

        /*@Value("${persistence.datasource.pool.initialSize:2}")
        private String databaseInitialSize;

        @Value("${persistence.datasource.pool.maxTotal:10}")
        private String databaseMaxTotal;*/

        @Bean
        public DataSource jpaDataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(databaseDriverClass);
            dataSource.setUrl(databaseConnectionUrl);
            dataSource.setUsername(databaseUserName);
            dataSource.setPassword(databasePassword);
            dataSource.setPoolPreparedStatements(true);
            //dataSource.setInitialSize(Integer.parseInt(databaseInitialSize));
            //dataSource.setMaxTotal(Integer.parseInt(databaseMaxTotal));

            return dataSource;
        }
    }

    @Bean
    @Profile("test")
    public DataSource jpaDataSource() {
        EmbeddedDatabaseBuilder builder 
                = new EmbeddedDatabaseBuilder();

        return builder
                .setType(
                        EmbeddedDatabaseType.HSQL)
                .setName(
                        "example_rest_api")
                .build();
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties 
                = new Properties();

        properties.setProperty(
                "hibernate.dialect",
                databaseDialect);

        properties.setProperty(
                "hibernate.show_sql",
                showSql);

        properties.setProperty(
                "hibernate.format_sql",
                formatSql);

        properties.setProperty(
                "hibernate.use_sql_comments",
                useSqlComments);

        properties.setProperty(
                "hibernate.generate_statistics",
                "true");

        return properties;
    }
   
    @Bean
    @Profile("test")
    @DependsOn("jpaProperties")
    public Properties jpaTestProperties(
            Properties jpaProperties) {

        jpaProperties.setProperty(
                "hibernate.hbm2ddl.auto",
                "create");

        jpaProperties.setProperty(
                "hibernate.hbm2ddl.import_files", 
                "/META-INF/hibernate/import.sql");

        return jpaProperties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource jpaDataSource,
            Properties jpaProperties) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory
                = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter());

        entityManagerFactory.setPersistenceUnitName(
                "persistence-unit");

        entityManagerFactory.setDataSource(
                jpaDataSource);

        entityManagerFactory.setJpaProperties(
                jpaProperties());

        /*entityManagerFactory.setMappingResources(
                "META-INF/hibernate/Person.hbm.xml",
                "META-INF/hibernate/User.hbm.xml",
                "META-INF/hibernate/Tenant.hbm.xml",
                "META-INF/hibernate/StoredEvent.hbm.xml",
                "META-INF/hibernate/Reservation.hbm.xml");*/

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager transactionManager 
                = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(
                entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
