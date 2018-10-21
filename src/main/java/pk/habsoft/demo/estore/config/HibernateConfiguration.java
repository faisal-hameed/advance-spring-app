package pk.habsoft.demo.estore.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "pk.habsoft.demo.estore.db")
@EnableTransactionManagement
public class HibernateConfiguration {

	@Value("${db.driver}")
	private String DB_DRIVER;

	@Value("${db.password}")
	private String DB_PASSWORD;

	@Value("${db.url}")
	private String DB_URL;

	@Value("${db.username}")
	private String DB_USERNAME;

	@Value("${hibernate.dialect}")
	private String HIBERNATE_DIALECT;

	@Value("${hibernate.show_sql}")
	private String HIBERNATE_SHOW_SQL;

	@Value("${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Value("${entitymanager.packagesToScan}")
	private String ENTITYMANAGER_PACKAGES_TO_SCAN;

	/**
	 * Session factory.
	 *
	 * @return the local session factory bean
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// ClassPathResource config = new
		// ClassPathResource("hibernate.cfg.xml");
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		// sessionFactory.setConfigLocation(config);
		sessionFactory.setDataSource(dbcpDataSource());
		sessionFactory.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	/**
	 * Data source using DBCP connection pool.
	 *
	 * @return the data source
	 */
	@Bean
	public DataSource dbcpDataSource() {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(DB_DRIVER);
		datasource.setUrl(DB_URL);
		System.err.println("Using database : " + DB_URL);
		datasource.setUsername(DB_USERNAME);
		datasource.setPassword(DB_PASSWORD);

		return datasource;
	}

	/**
	 * Hibernate properties.
	 *
	 * @return the properties
	 */
	private Properties hibernateProperties() {
		// This will be loaded automaticall by hibernate.properties
		Properties properties = new Properties();
		properties.put("hibernate.dialect", HIBERNATE_DIALECT);
		properties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		// properties.put("hibernate.format_sql",
		// environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);

		return properties;
	}
}
