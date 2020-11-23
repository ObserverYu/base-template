package org.chen.config.mysql.shardingrule;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 用户精确分片算法
 *  
 * @author YuChen
 * @date 2020/8/24 9:48
 **/
 
public class ShopPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data source or table's name
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long value = shardingValue.getValue();
        Long tb = value % 4;
        for (String s:availableTargetNames){
            String[] split = s.split("_");
            Long res = new Long(split[1]);
            if(tb.equals(res)){
                return s;
            }
        }
        return "electric_0";
    }
}
