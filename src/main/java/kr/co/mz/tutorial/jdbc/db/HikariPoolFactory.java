package kr.co.mz.tutorial.jdbc.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class HikariPoolFactory {
    public static void main(String[] args) throws SQLException, IOException {
        var dataSource = createHikariDataSource();
        System.out.println(dataSource);
        var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(
                "show tables");
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    private static Properties loadProperties() throws IOException {
        var properties = new Properties();
        try (var is = HikariPoolFactory.class.getClassLoader().getResourceAsStream("db/hikari.properties")) {
            properties.load(is);
        }
        return properties;
    }

    public static DataSource createHikariDataSource() throws IOException {
        // Examines both filesystem and classpath for .properties file
        var properties = loadProperties();
        HikariConfig config = new HikariConfig(properties);
        return new HikariDataSource(config);
    }
}