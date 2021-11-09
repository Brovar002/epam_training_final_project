package by.goncharov.epamsound.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServletSessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    public void extractValues(final HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        requestAttributes = new HashMap<>();
        Enumeration<String> attr = request.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }
        sessionAttributes = new HashMap<>();
        HttpSession session = request.getSession(true);
        attr = session.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }
    public void insertAttributes(final HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }
    public String getRequestParameter(final String key) {
        if (!requestParameters.isEmpty()) {
            String[] parameters = requestParameters.get(key);
            return  parameters != null ? parameters[0] : null;
        } else {
            return null;
        }
    }
    public void setRequestParameters(final Map<String,
            String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }
    public Object getRequestAttribute(final String key) {
        return requestAttributes.get(key);
    }
    public void setRequestAttribute(final String key, final Object value) {
        requestAttributes.put(key, value);
    }
    public Object getSessionAttribute(final String key) {
        return sessionAttributes.get(key);
    }
    public void setSessionAttribute(final String key, final Object value) {
        sessionAttributes.put(key, value);
    }
}
