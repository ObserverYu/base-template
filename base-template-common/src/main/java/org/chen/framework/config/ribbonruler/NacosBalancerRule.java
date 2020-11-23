package org.chen.framework.config.ribbonruler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于nacos实例权重和实例元数据的负载均衡规则
 *  
 * @author YuChen
 * @date 2020/1/17 16:59
 **/

@Slf4j
public class NacosBalancerRule extends AbstractLoadBalancerRule {

	@Autowired
	private NacosDiscoveryProperties nacosDiscoveryProperties;
	/**
	 * Concrete implementation should implement this method so that the configuration set via
	 * {@link IClientConfig} (which in turn were set via Archaius properties) will be taken into consideration
	 *
	 * @param clientConfig
	 */
	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {

	}

	/**
	* 从nacos获取元数据和权重数据,进行负载均衡
	*
	* @param
	* @return
	* @author YuChen
	* @date 2020/1/17 17:04
	*/
	@Override
	public Server choose(Object key) {
		// 负载均衡规则：优先选择同集群下，符合metadata的实例
		// 如果没有，就选择所有集群下，符合metadata的实例

		// 1. 查询所有实例 A
		// 2. 筛选元数据匹配的实例 B
		// 3. 筛选出同cluster下元数据匹配的实例 C
		// 4. 如果C为空，就用B
		// 5. 随机选择实例
		try {
			String clusterName = this.nacosDiscoveryProperties.getClusterName();
			String targetVersion = this.nacosDiscoveryProperties.getMetadata().get("target-version");

			DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
			String name = loadBalancer.getName();

			NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();

			// 所有实例
			List<Instance> instances = namingService.selectInstances(name, true);

			List<Instance> metadataMatchInstances = instances;
			// 如果配置了版本映射，那么只调用元数据匹配的实例
			if (StrUtil.isNotBlank(targetVersion)) {
				metadataMatchInstances = instances.stream()
						.filter(instance -> Objects.equals(targetVersion, instance.getMetadata().get("version")))
						.collect(Collectors.toList());
				if (CollectionUtils.isEmpty(metadataMatchInstances)) {
					log.warn("未找到元数据匹配的目标实例！请检查配置。targetVersion = {}, instance = {}", targetVersion, instances);
					return null;
				}
			}

			List<Instance> clusterMetadataMatchInstances = metadataMatchInstances;
			// 如果配置了集群名称，需筛选同集群下元数据匹配的实例
			if (StrUtil.isNotBlank(clusterName)) {
				clusterMetadataMatchInstances = metadataMatchInstances.stream()
						.filter(instance -> Objects.equals(clusterName, instance.getClusterName()))
						.collect(Collectors.toList());
				if (CollectionUtils.isEmpty(clusterMetadataMatchInstances)) {
					clusterMetadataMatchInstances = metadataMatchInstances;
					log.warn("发生跨集群调用。clusterName = {}, targetVersion = {}, clusterMetadataMatchInstances = {}", clusterName, targetVersion, clusterMetadataMatchInstances);
				}
			}

			Instance instance = ExtendBalancer.getHostByRandomWeight2(clusterMetadataMatchInstances);
			return new NacosServer(instance);
		} catch (Exception e) {
			log.warn("发生异常", e);
			return null;
		}
	}
}
