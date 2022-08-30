package demo.application.config
//import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.boot.orm.jpa.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource
import org.springframework.context.annotation.Bean

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource
import javax.persistence.*
import org.springframework.orm.jpa.vendor.Database;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.tomcat.jdbc.pool.PoolProperties;

@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories
public class DataSourceConfig {

    @Autowired
    private demo.application.properties.yaml.DatasourceProperties datasourceProperties

    //private JndiDataSourceLookup lookup = new JndiDataSourceLookup();




    @Resource
    @Bean(name = "dataSource")
    public DataSource getDataSource(){
        HikariConfig config = new HikariConfig();
        config.setPoolName("dbpool");
        config.setJdbcUrl(datasourceProperties.url);
        config.setDriverClassName(datasourceProperties.driverclassname);
        config.setUsername(datasourceProperties.username);
        config.setPassword(datasourceProperties.password);
        config.setMinimumIdle(0);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(35000);
        config.setMaxLifetime(45000);
        config.setAutoCommit(true);
        config.setValidationTimeout(5000);
        config.setMaximumPoolSize(4);
        config.setAllowPoolSuspension(false);
        config.setReadOnly(false);
        // NOTE: if query takes longer than 10 secs, you have issues!!!!!
        config.setLeakDetectionThreshold(10000);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        return new HikariDataSource(config);
    }


    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(getDataSource());
        entityManagerFactory.setPackagesToScan("demo.application.domain");
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactory.setJpaProperties(getHibernateProperties());
        return entityManagerFactory;
    }


    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", datasourceProperties.hibernate.dialect);
        properties.put("hibernate.show_sql", datasourceProperties.hibernate.showSql);
        properties.put("hibernate.format_sql", "true");
        return properties;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean getSessionFactory(DataSource datasource) throws Exception {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(getHibernateProperties());
        sessionFactory.setPackagesToScan("demo.application.domain");
        return sessionFactory
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

/*
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory);
        return hibernateTransactionManager;
    }
    */





}
