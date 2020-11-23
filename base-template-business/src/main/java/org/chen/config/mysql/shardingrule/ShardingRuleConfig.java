package org.chen.config.mysql.shardingrule;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * 分库分表配置
 *  
 * @author YuChen
 * @date 2020/8/21 14:21
 **/

@Configuration
public class ShardingRuleConfig {

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource",name = "mode",havingValue = "sharding")
    public ShardingRuleConfiguration shardingRuleConfiguration(){
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        shardingRuleConfig.getTableRuleConfigs().add(getOrderTestRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTestRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getShopTestRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getUserTestRuleConfiguration());

        shardingRuleConfig.getBindingTableGroups().add("order_test, order_item_test");

        shardingRuleConfig.getBroadcastTables().add("voice");
        shardingRuleConfig.getBroadcastTables().add("banner");
        shardingRuleConfig.getBroadcastTables().add("city");

        StandardShardingStrategyConfiguration defaultDSShardingStrategy
                = new StandardShardingStrategyConfiguration("user_id"
                , new UserPreciseShardingAlgorithm()
        );

        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "electric_${user_id % 4}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id",new OrderPreciseShardingAlgorithm()));
        return shardingRuleConfig;
    }

    private KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("worker.id","22");
        properties.setProperty("max.vibration.offset","15");
        return new KeyGeneratorConfiguration("SNOWFLAKE", "order_id",properties);
    }

    public TableRuleConfiguration getOrderTestRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("order_test", "electric_${0..3}.order_test_${0..3}");
        //result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        Properties properties = new Properties();
        properties.setProperty("worker.id","22");
        properties.setProperty("max.vibration.offset","15");
        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("LEFT-SNOWFLAKE","order_id",properties));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",new UserPreciseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id",new OrderPreciseShardingAlgorithm()));
        return result;
    }

    public TableRuleConfiguration getOrderItemTestRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("order_item_test", "electric_${0..3}.order_item_test_${0..3}");
        Properties properties = new Properties();
        properties.setProperty("worker.id","22");
        properties.setProperty("max.vibration.offset","15");
        //result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("LEFT-SNOWFLAKE","order_item_id",properties));
        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE","order_item_id",properties));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id",new OrderPreciseShardingAlgorithm()));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",new UserPreciseShardingAlgorithm()));
        return result;
    }

    public TableRuleConfiguration getShopTestRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("shop_test", "electric_${0..3}.shop_test");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("s_id",new ShopPreciseShardingAlgorithm()));
        Properties properties = new Properties();
        properties.setProperty("worker.id","22");
        properties.setProperty("max.vibration.offset","15");
        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE","s_id",properties));
        return result;
    }

    public TableRuleConfiguration getUserTestRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("user_test", "electric_${0..3}.user_test");
//        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",new UserPreciseShardingAlgorithm()));
//        Properties properties = new Properties();
//        properties.setProperty("worker.id","22");
//        properties.setProperty("max.vibration.offset","15");
//        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE","user_id",properties));


        return result;
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",9999);
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write("GET /health HTTP/1.1 \r\n");
        writer.write("\r\n");
        writer.write("\r\n");
        writer.flush();
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String s;
        while((s = reader.readLine()) != null){
            builder.append(s);
        }
        inputStream.close();
        System.out.println(builder);
    }
}
