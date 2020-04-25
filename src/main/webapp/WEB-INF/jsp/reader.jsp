<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/17/2020
  Time: 10:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">
    <title>INFOLIVE:<fmt:message key="read.online"/></title>
</head>
<body>
<embed src="<c:out value="${pageContext.request.contextPath}"/>/reader/${pathToFile}" width="100%" height="100%"/>
</body>
</html>
