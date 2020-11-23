package org.chen.config.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据源配置
 *
 * @author YuChen
 * @date 2020/8/4 16:43
 **/

@Configuration
public class DataSourceConfig {


    @ConfigurationProperties("spring.datasource.single")
    @ConditionalOnProperty(prefix = "spring.datasource",name = "mode",havingValue = "single")
    @Bean
    public DataSource dataSourceSingle(){
        return DruidDataSourceBuilder.create().build();
    }

    @ConditionalOnProperty(prefix = "spring.datasource",name = "mode",havingValue = "sharding")
    @Bean
    public DataSource dataSourceSharding(ShardingProperty shardingProperty,ShardingRuleConfiguration shardingRuleConfiguration) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 创建分片druid数据源
        ShardingDSProperty[] sharding = shardingProperty.getSharding();
        for (ShardingDSProperty dsProperty : sharding){
            DruidDataSource ds = DruidDataSourceBuilder.create().build();
            Properties connectionProperties = dsProperty.getConnectionProperties();
            ds.setConnectProperties(connectionProperties);
            String driverClassName = dsProperty.getDriverClassName();
            ds.setDriverClassName(driverClassName);
            String filters = dsProperty.getFilters();
            ds.setFilters(filters);
            Integer initialSize = dsProperty.getInitialSize();
            ds.setInitialSize(initialSize);
            Integer maxActive = dsProperty.getMaxActive();
            ds.setMaxActive(maxActive);
            Integer maxPoolPreparedStatementPerConnectionSize = dsProperty.getMaxPoolPreparedStatementPerConnectionSize();
            ds.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            Integer maxWait = dsProperty.getMaxWait();
            ds.setMaxWait(maxWait);
            Integer minEvictableIdleTimeMillis = dsProperty.getMinEvictableIdleTimeMillis();
            ds.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            Integer minIdle = dsProperty.getMinIdle();
            ds.setMinIdle(minIdle);
            String password = dsProperty.getPassword();
            ds.setPassword(password);
            Boolean poolPreparedStatements = dsProperty.getPoolPreparedStatements();
            ds.setPoolPreparedStatements(poolPreparedStatements);
            Boolean testOnBorrow = dsProperty.getTestOnBorrow();
            ds.setTestOnBorrow(testOnBorrow);
            Boolean testOnReturn = dsProperty.getTestOnReturn();
            ds.setTestOnReturn(testOnReturn);
            Boolean testWhileIdle = dsProperty.getTestWhileIdle();
            ds.setTestWhileIdle(testWhileIdle);
            Integer timeBetweenEvictionRunsMillis = dsProperty.getTimeBetweenEvictionRunsMillis();
            ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            String url = dsProperty.getUrl();
            ds.setUrl(url);
            Boolean useGlobalDataSourceStat = dsProperty.getUseGlobalDataSourceStat();
            ds.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
            String username = dsProperty.getUsername();
            ds.setUsername(username);
            String validationQuery = dsProperty.getValidationQuery();
            ds.setValidationQuery(validationQuery);
            String dsname = dsProperty.getDsname();
            ds.setName(dsname);
            dataSourceMap.put(dsname,ds);
        }
        Properties properties = new Properties();
        //properties.setProperty("sql.show","true");
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, properties);
    }

    /**
     * 配置监控服务器
     * @return 返回监控注册的servlet对象
     * @author SimpleWu
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
        servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
        // 添加控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "yuchen");
        servletRegistrationBean.addInitParameter("loginPassword", "yuchenha");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }
}
