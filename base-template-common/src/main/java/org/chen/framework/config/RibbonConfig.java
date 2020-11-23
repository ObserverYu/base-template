package org.chen.framework.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.chen.framework.config.ribbonruler.NacosBalancerRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ribbon配置
 *  
 * @author YuChen
 * @date 2020/1/17 16:45
 **/

@Configuration
public class RibbonConfig {

	@Value("${ribbon.nacosBalancer}")
	private boolean nacosBalancer;

	/**
	* 根据配置使用负载均衡规则
	*
	* @param
	* @return
	* @author YuChen
	* @date 2020/1/17 17:18
	*/
	@Bean
	public IRule ribbonRule(){
		if(nacosBalancer){
			return new NacosBalancerRule();
		}
		return new RoundRobinRule();
	}
}
