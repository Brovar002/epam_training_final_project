package by.goncharov.epamsound.servlet;

import by.goncharov.epamsound.manager.ConnectionPool;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SoundServlet extends HttpServlet
        implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent
                                               servletContextEvent) {
    }
    @Override
    public void contextDestroyed(final ServletContextEvent
                                             servletContextEvent) {
        ConnectionPool.getInstance().terminatePool();
    }

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void processRequest(final HttpServletRequest request,
                                  final HttpServletResponse response)
            throws ServletException, IOException {

        ServletSessionRequestContent servletSessionRequestContent
                = new ServletSessionRequestContent();
        servletSessionRequestContent.extractValues(request);
    }
}

