package by.goncharov.epamsound.controller.filter;

import by.goncharov.epamsound.beans.User;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {
    private static final String PATH_PARAMETER = "INDEX_PATH";
    private static final String USER_ATTRIBUTE = "user";
    private String indexPath;
    @Override
    public void init(final FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(PATH_PARAMETER);
    }
    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute(
                USER_ATTRIBUTE);
        if (user == null || user.getRole() != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
    }
}
