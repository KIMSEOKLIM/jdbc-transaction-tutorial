package kr.co.mz.tutorial.servletListener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HikariCPInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/webchat?serverTimezone=UTC&characterEncoding=UTF-8");
        config.setUsername("webchat");
        config.setPassword("webchat!");
        config.setMaximumPoolSize(50);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        var dataSource = new HikariDataSource(config);
        servletContextEvent.getServletContext().setAttribute("dataSource", dataSource); // Dao에서 dataSource 불러와 connection한뒤에 dto에 값 담아서 dto객체를 서블릿으로 옮겨준다.
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

