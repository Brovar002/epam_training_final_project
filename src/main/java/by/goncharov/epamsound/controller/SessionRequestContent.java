package by.goncharov.epamsound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Session request content.
 * @author Goncharov Daniil
 * @version 1.0
 */
public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    /**
     * Extract values.
     *
     * @param request the request
     */
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

    /**
     * Insert attributes.
     *
     * @param request the request
     */
    public void insertAttributes(final HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Gets request parameter.
     *
     * @param key the key
     * @return the request parameter
     */
    public String getRequestParameter(final String key) {
        if (!requestParameters.isEmpty()) {
            String[] parameters = requestParameters.get(key);
            return  parameters != null ? parameters[0] : null;
        } else {
            return null;
        }
    }

    /**
     * Sets request parameters.
     *
     * @param requestParameters the request parameters
     */
    public void setRequestParameters(final Map<String,
            String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    /**
     * Gets request attribute.
     *
     * @param key the key
     * @return the request attribute
     */
    public Object getRequestAttribute(final String key) {
        return requestAttributes.get(key);
    }

    /**
     * Sets request attribute.
     *
     * @param key   the key
     * @param value the value
     */
    public void setRequestAttribute(final String key, final Object value) {
        requestAttributes.put(key, value);
    }

    /**
     * Gets session attribute.
     *
     * @param key the key
     * @return the session attribute
     */
    public Object getSessionAttribute(final String key) {
        return sessionAttributes.get(key);
    }

    /**
     * Sets session attribute.
     *
     * @param key   the key
     * @param value the value
     */
    public void setSessionAttribute(final String key, final Object value) {
        sessionAttributes.put(key, value);
    }
}
