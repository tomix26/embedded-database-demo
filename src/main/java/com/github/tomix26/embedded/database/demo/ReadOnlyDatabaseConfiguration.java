package com.github.tomix26.embedded.database.demo;

import com.github.tomix26.embedded.database.demo.annotation.ReadOnlyRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.github.tomix26.embedded.database.demo.domain",
        includeFilters = @Filter(ReadOnlyRepository.class),
        entityManagerFactoryRef = "readOnlyEntityManagerFactory"
)
public class ReadOnlyDatabaseConfiguration {

    @Profile("!test")
    @Bean("readOnlyDataSource")
    public HikariDataSource readOnlyDataSource(DataSourceProperties dataSourceProperties) throws SQLException {
        HikariDataSource readOnlyDataSource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        readOnlyDataSource.setReadOnly(true);
        readOnlyDataSource.getConnection().setReadOnly(true);

        return readOnlyDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("readOnlyDataSource") DataSource readOnlyDataSource) {

        return builder
                .dataSource(readOnlyDataSource)
                .packages("com.github.tomix26.embedded.database.demo.domain")
                .persistenceUnit("readOnly")
                .build();
    }
}
