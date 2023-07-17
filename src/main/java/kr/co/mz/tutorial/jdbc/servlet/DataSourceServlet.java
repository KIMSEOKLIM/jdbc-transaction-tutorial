package kr.co.mz.tutorial.jdbc.servlet;

import kr.co.mz.tutorial.RuntimeServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import static kr.co.mz.tutorial.Constants.DATASOURCE_CONTEXT_KEY;

public abstract class DataSourceServlet extends HttpServlet {
    protected DataSource dataSource;

    @Override
    public void init() {
        this.dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
    }

    public void sendRedirect(HttpServletResponse response, String redirectUrl) {
        try {
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            throw new RuntimeServletException(e.getMessage(), e);
        }
    }

    public void forward(HttpServletRequest request, HttpServletResponse response, String forwardUri) {
        try {
            request.getRequestDispatcher(forwardUri).forward(request, response);
        } catch (Exception e) {
            throw new RuntimeServletException(e.getMessage(), e);
        }
    }
}
