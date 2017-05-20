package cn.artisantc.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Depending active spring profile, lookup RDBMS DataSource from JNDI.
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration(value = "DataSourceConfig")
@PropertySource(value = {
        "classpath:datasource.properties"
})
public class DataSourceConfig {

    private final Environment env;

    @Autowired
    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();

        return dsLookup.getDataSource(env.getProperty("data.source.jndi.name"));
    }

//    @Bean
//    @Profile(value = "test")
//    public DataSource dataSourceForTest() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//    }
}
