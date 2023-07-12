package kr.co.mz.tutorial.servlet.handler;

import kr.co.mz.tutorial.jdbc.dao.CustomerDao;
import kr.co.mz.tutorial.jdbc.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class LoginHandler extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer();
        CustomerDao customerDao;
        customer.setCustomerId(request.getParameter("username"));
        customer.setPassword(request.getParameter("password"));
        var datasource = (DataSource) getServletContext().getAttribute("dataSource");
        try {
            customerDao = new CustomerDao(customer, datasource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int[] result;
        try {
            result = customerDao.compareInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (result[0] == 1) {
            response.sendRedirect("/posts");
            getServletContext().setAttribute("seq", result[1]);
        } else {
            response.sendRedirect("/");
        }

    }
}
