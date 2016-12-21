/**
* Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at

*     http://www.apache.org/licenses/LICENSE-2.0

* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
**/

package io._29cu.usmserver.configurations.database;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties({DataSourceResource.class, JpaAdapterResource.class})
public class JpaConfiguration {

    @Autowired
    private final DataSourceResource dataSourceResource;

    @Autowired
    private final JpaAdapterResource jpaResource;

    @Autowired
    public JpaConfiguration(DataSourceResource dataSourceResource, JpaAdapterResource jpaResource) {
        this.dataSourceResource = dataSourceResource;
        this.jpaResource = jpaResource;
    }

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {

        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.cj.jdbc.Driver());

        dataSource.setUrl(dataSourceResource.getUrl());
        dataSource.setUsername(dataSourceResource.getUsername());
        dataSource.setPassword(dataSourceResource.getPassword());

        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(jpaResource.getGenerateddl());
        jpaVendorAdapter.setShowSql(jpaResource.getShowsql());
        jpaVendorAdapter.setDatabasePlatform(jpaResource.getDialect());

        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {

        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setPackagesToScan("io._29cu.usmserver.core.model.entities");
        lef.setDataSource(dataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.jdbc.fetch_size", "100");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "io._29cu.usmserver.configurations.database.AppMySQLDialect");

        lef.setJpaProperties(properties);
        return lef;
    }
}

