package com.demo.config.poc;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "multitenancy.poc")
public class MultiTenantProperties {

	private List<DataSourceProperties> dataSourcesProps;

	public List<DataSourceProperties> getDataSources() {
		return this.dataSourcesProps;
	}

	public void setDataSources(List<DataSourceProperties> dataSourcesProps) {
		this.dataSourcesProps = dataSourcesProps;
	}

	public static class DataSourceProperties extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {

		private String tenantId;
		private int initialPoolSize;
        private int minPoolSize;
        private int acquireIncrement;
        private int maxPoolSize;

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}

		public int getInitialPoolSize() {
			return initialPoolSize;
		}

		public void setInitialPoolSize(int initialPoolSize) {
			this.initialPoolSize = initialPoolSize;
		}

		public int getMinPoolSize() {
			return minPoolSize;
		}

		public void setMinPoolSize(int minPoolSize) {
			this.minPoolSize = minPoolSize;
		}

		public int getAcquireIncrement() {
			return acquireIncrement;
		}

		public void setAcquireIncrement(int acquireIncrement) {
			this.acquireIncrement = acquireIncrement;
		}

		public int getMaxPoolSize() {
			return maxPoolSize;
		}

		public void setMaxPoolSize(int maxPoolSize) {
			this.maxPoolSize = maxPoolSize;
		}
		
		
	}
}