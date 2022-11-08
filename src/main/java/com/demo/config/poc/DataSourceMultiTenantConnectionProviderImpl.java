package com.demo.config.poc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Map<String, ComboPooledDataSource> dataSources;

	@Override
	protected DataSource selectAnyDataSource() {
		return this.dataSources.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return this.dataSources.get(tenantIdentifier);
	}
	
	@Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        return this.dataSources.get(tenantIdentifier).getConnection();
    }

}