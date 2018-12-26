package com.tmf.configuration;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import infinispan.autoconfigure.embedded.InfinispanCacheConfigurer;
import infinispan.autoconfigure.embedded.InfinispanGlobalConfigurer;

@Configuration
public class InfinispanConfiguration {

	public static final String TEST_CACHE_NAME = "test-cache-name";
	private static final String JMX_DOMAIN = "test.infinispan";
	private static final String TEST_CLUSTER = "test-cluster";

	@Bean
	public InfinispanGlobalConfigurer globalConfigurer() {
		return () -> {
			return new GlobalConfigurationBuilder().transport().defaultTransport()
					.addProperty("configurationFile", "default-configs/default-jgroups-tcp.xml")
					.clusterName(TEST_CLUSTER)
					.globalJmxStatistics()
						.jmxDomain(JMX_DOMAIN)
						.enable()
					.build();
		};
	}

	@Bean
	public InfinispanCacheConfigurer cacheConfigurer() {
		return manager -> {
			final org.infinispan.configuration.cache.Configuration ispnConfig = new ConfigurationBuilder()
					.clustering()
					.cacheMode(CacheMode.DIST_SYNC)
					.sync()
					.build();

			manager.defineConfiguration(TEST_CACHE_NAME, ispnConfig);
		};
	}
}
