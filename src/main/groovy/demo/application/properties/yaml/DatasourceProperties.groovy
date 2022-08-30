package demo.application.properties.yaml;

import io.beapi.api.properties.yaml.factory.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "db.datasource")
@PropertySource(value = "file:\${user.home}/.boot/\${spring.profiles.active}/beapi_db.yaml", factory = YamlPropertySourceFactory.class)
public class DatasourceProperties {

    Boolean jmxexport
    String driverclassname
    String dialect
    String username
    String password
    String url
    String dbCreate = 'none'
    DbProps properties = new DbProps()
    Hibernate hibernate  = new Hibernate();


    void setJmxexport(Boolean jmxExport) { this.jmxexport = jmxexport }
    void setDriverclassname(String driverclassname) { this.driverclassname = driverclassname }
    void setDialect(String dialect) { this.dialect = dialect }
    void setUsername(String username) { this.username = username }
    void setPassword(String password) { this.password = password }
    void setUrl(String url) { this.url = url }
    void setDbCreate(String dbCreate) { this.dbCreate = dbCreate }
    void setProps(DbProps props) { this.properties = properties }
    //void setHibernate(Hibernate hibernate) { this.hibernate = hibernate }


    Boolean getJmxexport() { return jmxexport }
    String getDriverclassname() { return this.driverclassname }
    String getDialect() { return dialect }
    String getUsername() { return username }
    String getPassword() { return password }
    String getUrl() { return url }
    String getDbCreate() { return dbCreate }
    DbProps getProps() { return properties }
    Hibernate getHibernate() { return hibernate }



    public static class DbProps {
        private Boolean pooled = true
        private Boolean jmxEnabled = true
        private Integer initialSize = 5
        private Integer maxActive = 50
        private Integer minIdle = 5
        private Integer maxIdle = 25
        private Integer maxWait = 10000
        private Integer maxAge = 600000
        private Integer timeBetweenEvictionRunsMillis = 5000
        private Integer minEvictableIdleTimeMillis = 60000
        private String validationQuery = "SELECT 1"
        private Integer validationQueryTimeout = 3
        private Integer validationInterval = 15000
        private Boolean testOnBorrow = true
        private Boolean testWhileIdle = true
        private Boolean testOnReturn = false
        private String jdbcInterceptors = "ConnectionState"
        // "java.sql.Connection.TRANSACTION_READ_COMMITTED"
        private Integer defaultTransactionIsolation = 2

        void setPooled(Boolean pooled) { this.pooled = pooled }
        void setJmxEnabled(Boolean jmxEnabled) { this.jmxEnabled = jmxEnabled }
        void setInitialSize(Integer initialSize) { this.initialSize = initialSize }
        void setMaxActive(Integer maxActive) { this.maxActive = maxActive }
        void setMinIdle(Integer minIdle) { this.minIdle = minIdle }
        void setMaxIdle(Integer maxIdle) { this.maxIdle = maxIdle }
        void setMaxWait(Integer maxWait) { this.maxWait = maxWait }
        void setMaxAge(Integer maxAge) { this.maxAge = maxAge }
        void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) { this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis }
        void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) { this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis }
        void setValidationQuery(String validationQuery) { this.validationQuery = validationQuery }
        void setValidationQueryTimeout(Integer validationQueryTimeout) { this.validationQueryTimeout = validationQueryTimeout }
        void setValidationInterval(Integer validationInterval) { this.validationInterval = validationInterval }
        void setTestOnBorrow(Boolean testOnBorrow) { this.testOnBorrow = testOnBorrow }
        void setTestWhileIdle(Boolean testWhileIdle) { this.testWhileIdle = testWhileIdle }
        void setTestOnReturn(Boolean testOnReturn) { this.testOnReturn = testOnReturn }
        void setJdbcInterceptors(String jdbcInterceptors) { this.jdbcInterceptors = jdbcInterceptors }
        void setDefaultTransactionIsolation(Integer defaultTransactionIsolation) { this.defaultTransactionIsolation = defaultTransactionIsolation }


        Boolean getPooled() { return pooled }
        Boolean getJmxEnabled() { return jmxEnabled }
        Integer getInitialSize() { return initialSize }
        Integer getMaxActive() { return maxActive }
        Integer getMinIdle() { return minIdle }
        Integer getMaxIdle() { return maxIdle }
        Integer getMaxWait() { return maxWait }
        Integer getMaxAge() { return maxAge }
        Integer getTimeBetweenEvictionRunsMillis() { return timeBetweenEvictionRunsMillis }
        Integer getMinEvictableIdleTimeMillis() { return minEvictableIdleTimeMillis }
        String getValidationQuery() { return validationQuery }
        Integer getValidationQueryTimeout() { return validationQueryTimeout }
        Integer getValidationInterval() { return validationInterval }
        Boolean getTestOnBorrow() { return testOnBorrow }
        Boolean getTestWhileIdle() { return testWhileIdle }
        Boolean getTestOnReturn() { return testOnReturn }
        String getJdbcInterceptors() { return jdbcInterceptors }
        Integer getDefaultTransactionIsolation() { return defaultTransactionIsolation }
    }


    public static class Hibernate {
        private String dialect
        private String showSql = ''

        void setDialect(String dialect) { this.dialect = dialect }
        void setShowSql(String showSql) { this.showSql = showSql }

        String getDialect() { return dialect }
        String getShowSql() { return showSql }
    }


    @Override
    public String toString() {
        return "YamlFooProperties{" +
                "username='" + username + '\'' +
                ", driverclassname=" + driverclassname +
                '}';
    }
}
