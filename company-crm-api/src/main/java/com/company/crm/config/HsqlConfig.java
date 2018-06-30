package com.company.crm.config;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Configuration to start hsql database
 */
@Configuration
@Import({HsqlDataSourceConfig.class})
@ComponentScan({ "com.company" })
public class HsqlConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate getNamedParameterJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

	@PostConstruct
	public void startDBManager() {
		DatabaseManagerSwing.main(new String[] {
		        "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""
		});
	}
}
