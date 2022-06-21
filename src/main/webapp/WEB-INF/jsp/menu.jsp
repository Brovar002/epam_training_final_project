<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content" />
<!DOCTYPE html>
<html lang="en">>
<head>
    <meta charset="UTF-8">

    <link href="${pageContext.request.contextPath}/WEB-INF/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=main">
                <fmt:message key="menu.brand"/>
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                    <security:authorize>
                        <li><a href="${pageContext.request.contextPath}/WEB-INF/jsp/user/profile.jsp"><span class="glyphicon glyphicon-user" style="color: gainsboro"></span> ${user.login}</a></li>
                        <li><a href="${pageContext.request.contextPath}/controller?command=logout"><span class="glyphicon glyphicon-log-out" style="color: gainsboro"></span> <fmt:message key="menu.logout"/></a></li>
                    </security:authorize>
                        <li><a href="${pageContext.request.contextPath}/WEB-INF/jsp/login.jsp"><fmt:message key="menu.login"/></a></li>
                        <li><a href="${pageContext.request.contextPath}/WEB-INF/jsp/signup.jsp"><fmt:message key="menu.signup"/></a></li>
            </ul>
            <form class="navbar-form navbar-right" name="search" action="${pageContext.request.contextPath}/controller">
                <input type="text" name="find" class="form-control" placeholder=<fmt:message key="menu.search"/>>
                <button type="submit" class="btn btn-link" name="command"
                        value="search"><span class="glyphicon glyphicon-search"></span></button>
            </form>
        </div>
    </div>
</nav>
</body>
</html>