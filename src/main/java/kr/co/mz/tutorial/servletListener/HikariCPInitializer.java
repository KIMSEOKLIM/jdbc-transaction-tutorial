package kr.co.mz.tutorial.servletListener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPInitializer {


    public static Connection getConnection() throws IOException, SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/webchat?serverTimezone=UTC&characterEncoding=UTF-8");
        config.setUsername("webchat");
        config.setPassword("webchat!");
        config.setDriverClassName("com.mysql.jdbc.Driver"); // ????

        DataSource dataSource = new HikariDataSource(config);
        Connection connection = dataSource.getConnection();

        return connection;
    }
}
