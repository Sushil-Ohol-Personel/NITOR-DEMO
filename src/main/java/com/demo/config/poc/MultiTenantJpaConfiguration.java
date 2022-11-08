package com.demo.config.poc;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.demo.config.poc.MultiTenantProperties.DataSourceProperties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mt.model.Actor;

@Configuration
@EnableConfigurationProperties({ MultiTenantProperties.class, JpaProperties.class })
@ImportResource(locations = { "classpath:applicationContent.xml" }) 
@EnableJpaRepositories
@EnableTransactionManagement
public class MultiTenantJpaConfiguration {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private MultiTenantProperties multiTenantProperties;

	@Bean(name = "dataSources" )
	public Map<String, ComboPooledDataSource> dataSources() throws PropertyVetoException {
		Map<String, ComboPooledDataSource> result = new HashMap<>();
				for (DataSourceProperties dsProperties : this.multiTenantProperties.getDataSources()) {
					ComboPooledDataSource ds = new ComboPooledDataSource();
					ds.setDriverClass(dsProperties.getDriverClassName());
					ds.setJdbcUrl(dsProperties.getUrl());
					ds.setUser(dsProperties.getUsername());
					ds.setPassword(dsProperties.getPassword());
					ds.setInitialPoolSize(dsProperties.getInitialPoolSize());
					ds.setMinPoolSize(dsProperties.getMinPoolSize());
					ds.setAcquireIncrement(dsProperties.getAcquireIncrement());
					ds.setMaxPoolSize(dsProperties.getMaxPoolSize());
					result.put(dsProperties.getTenantId(), ds);
				}
				
			
				
		return result;
	}

	@Bean
	public MultiTenantConnectionProvider multiTenantConnectionProvider() {
		// Autowires dataSourcesDvdRental
		return new DataSourceMultiTenantConnectionProviderImpl();
	}

	@Bean
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new TenantIdentifierResolverImpl();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
			CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

		Map<String, Object> hibernateProps = new LinkedHashMap<>();
		hibernateProps.putAll(this.jpaProperties.getProperties());
		hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
		// No dataSource is set to resulting entityManagerFactoryBean
		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		result.setPackagesToScan(new String[] { Actor.class.getPackage().getName() });
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setJpaPropertyMap(hibernateProps);

		return result;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return entityManagerFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}