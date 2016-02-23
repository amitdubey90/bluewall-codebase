package com.bluewall.feservices.config;

import com.bluewall.feservices.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan
public class AppConfig {
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(Constants.MYSQL_DRIVER);
		driverManagerDataSource.setUrl(Constants.MYSQL_RDS_URI);
		driverManagerDataSource.setUsername(Constants.MYSQL_RDS_USER);
		driverManagerDataSource.setPassword(Constants.MYSQL_RDS_PASSWORD);
		return driverManagerDataSource;
	}
}
