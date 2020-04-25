<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 18.01.2020
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <%--My Style--%>
    <link rel="stylesheet" type="text/css" href="css/simple_nav_bar.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">

    <title>INFOLIVE: <fmt:message key="sign.in"/></title>
</head>
<body>
<nav>
    <div class="navbarMenu">
        <div class="leftPartNavbar">
            <div class="brandNameField">
                <a class="navbar-brand" href="<c:out value="${pageContext.request.contextPath}"/>/media" title="<fmt:message key="home.page"/>">
                    <strong id="infoLifeLabel">INFOLIVE</strong></a>
            </div>
            <div class="LookingForMenu">

            </div>
        </div>
        <div class="centerLeftPartTriangle">
        </div>
        <div class="centerPartNavbar">
        </div>
        <div class="centerRightPartTriangle">
        </div>
        <div class="rightPartNavbar">
            <div id="userLogin">
                <a class="userLoginBtn" href="<c:out value="${pageContext.request.contextPath}"/>/registration"
                   title="<fmt:message key="title.registration"/>">
                    <img class="userLoginImg" src="icon/add-user-button.png" alt="user"></a>
            </div>
        </div>
    </div>
</nav>
<!-- main part -->
<main>
    <c:if test="${SUBSCRIPTION_DTO != null}">
        <div id="info-from-subscribe">
            <h3><fmt:message key="To.subscribe.you.must.first.log.in"/></h3>
        </div>
    </c:if>
    <c:if test="${INVALID != null}">
        <jsp:useBean id="INVALID" scope="session" type="java.lang.String"/>
        <div id="not-correct-data">
            <h3><fmt:message key="You.have.entered.incorrect.data"/></h3>
        </div>
        <div id="loginOutsideBorder">
            <div>
                <h3><fmt:message key="header.info.for.user"/></h3>
            </div>
            <form method="post" action="authorization">
                <div>
                    <h2><fmt:message key="e.mail"/></h2>
                    <input class="inputField" type="email" required name="EMAIL_LOGIN"
                           placeholder="<fmt:message key="your.e.mail"/>"
                           pattern="^[\w!#$%&’*+/=?`{|}~^-]+(?:\.[\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$"
                           value="<c:out value="${INVALID}"/>">
                </div>
                <div>
                    <h2><fmt:message key="password.header"/>:</h2>
                    <input class="inputField" type="password" name="PASSWORD_LOGIN" required pattern="[A-Za-z0-9]{6,21}"
                           placeholder="<fmt:message key="password.placeholder"/>">
                </div>
                <c:if test="invalid" scope="request" var="Invalid">
                    invalid
                </c:if>
                <button id="loginBtn" name="SUBMIT_LOGIN" type="submit" title="<fmt:message key="login.submit"/>">
                    <fmt:message key="login.submit"/></button>
            </form>
<%--            <a href="#"><fmt:message key="i.forgot.my.password"/></a>--%>
            <a href="<c:out value="${pageContext.request.contextPath}"/>/registration"><fmt:message key="ref.to.registration"/></a>
        </div>
    </c:if>
    <c:if test="${INVALID == null}">
        <div id="loginOutsideBorder">
            <div>
                <h3><fmt:message key="header.info.for.user"/></h3>
            </div>
            <form method="post" action="authorization">
                <div>
                    <h2><fmt:message key="e.mail"/></h2>
                    <input class="inputField" type="email" required name="EMAIL_LOGIN"
                           placeholder="<fmt:message key="your.e.mail"/>"
                           pattern="^[\w!#$%&’*+/=?`{|}~^-]+(?:\.[\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$">
                </div>
                <div>
                    <h2><fmt:message key="password.header"/>:</h2>
                    <input class="inputField" type="password" name="PASSWORD_LOGIN" required pattern="[A-Za-z0-9]{6,21}"
                           placeholder="<fmt:message key="password.placeholder"/>">
                </div>
                <c:if test="invalid" scope="request" var="Invalid">
                    invalid
                </c:if>
                <button id="loginBtn" name="SUBMIT_LOGIN" type="submit" title="<fmt:message key="login.submit"/>">
                    <fmt:message key="login.submit"/></button>
            </form>
<%--            <a href="#"><fmt:message key="i.forgot.my.password"/></a>--%>
            <a href="<c:out value="${pageContext.request.contextPath}"/>/registration"><fmt:message key="ref.to.registration"/></a>
        </div>
    </c:if>

</main>
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/login">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit">RU</button>
                <input type="hidden" name="URLFromRequest" value="/login">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/login">
            </form>
        </div>
    </div>
</footer>
</body>
</html>
