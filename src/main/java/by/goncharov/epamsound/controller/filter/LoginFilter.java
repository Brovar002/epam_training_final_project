package by.goncharov.epamsound.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    private static final String PATH_PARAMETER = "INDEX_PATH";
    private static final String TRUE = "true";
    private static final String IS_LOGIN_ATTRIBUTE = "is_login";
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
        Object isLogin = httpRequest.getSession().getAttribute(
                IS_LOGIN_ATTRIBUTE);
        if (isLogin == null || !TRUE.equals(isLogin.toString())) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
    }
}
