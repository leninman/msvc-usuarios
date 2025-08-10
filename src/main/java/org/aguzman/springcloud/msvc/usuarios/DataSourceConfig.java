package org.aguzman.springcloud.msvc.usuarios;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        System.out.println("Mysql: " + System.getenv("Mysql"));
        System.getenv().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });


        String rawUrl = System.getenv("MYSQL_URL"); // Ej: mysql://user:pass@host:port/dbname
        if (rawUrl == null || !rawUrl.startsWith("mysql://")) {
            throw new RuntimeException("MYSQL_URL no está definido o tiene formato incorrecto");
        }

        URI uri = URI.create(rawUrl);
        String[] userInfo = uri.getUserInfo().split(":");
        String username = userInfo[0];
        String password = userInfo[1];
        String jdbcUrl = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Opcional: configuración adicional
        dataSource.setMaximumPoolSize(10);
        dataSource.setConnectionTimeout(30000);

        return dataSource;
    }
}


