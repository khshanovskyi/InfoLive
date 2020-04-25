<jsp:useBean id="USER_IS_UNBLOCKED" scope="session" type="ua.nure.khshanovskyi.infoLife.entity.user.User"/>
<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/23/2020
  Time: 12:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <%--My Style--%>
    <link rel="stylesheet" type="text/css" href="css/nav_bar_with_search_field.css">
    <link rel="stylesheet" type="text/css" href="css/my_subscription.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">
    <title>INFOLIVE: <fmt:message key="my.subscriptions"/></title>
</head>

<body>
<%--NavBar Menu--%>
<tg:nav_bar_media_details/>
<main>
    <jsp:useBean id="subscriptionsList" scope="request"
                 type="java.util.List<ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO>"/>
    <c:if test="${not empty subscriptionsList}">
        <c:forEach items="${subscriptionsList}" var="subscribtionDTO">
            <h2 class="name-header"><b><c:out value="${subscribtionDTO.mediaName}"/></b></h2>
            <div class="list-subscribing-info">
                <div class="content">
                    <div class="left-part">
                        <h5><fmt:message key="media.name"/>:</h5>
                    </div>
                    <div class="right-part">
                        <h5><c:out value="${subscribtionDTO.mediaName}"/></h5>
                    </div>
                </div>
                <div class="content">
                    <div class="left-part">
                        <h5><fmt:message key="quantity.editions.in.month"/>:</h5>
                    </div>
                    <div class="right-part">
                        <h5><c:out value="${subscribtionDTO.quantityPublicationInMonth}"/></h5>
                    </div>
                </div>
                <div class="content">
                    <div class="left-part">
                       <h5><fmt:message key="price.per.month"/>:</h5>
                    </div>
                    <div class="right-part">
                        <h5><c:out value="${subscribtionDTO.price}"/></h5>
                    </div>
                </div>
                <div class="content">
                    <div class="left-part">
                        <h5><fmt:message key="subscribed.from"/>:</h5>
                    </div>
                    <div class="right-part">
                        <h5><c:out value="${subscribtionDTO.dateFrom}"/></h5>
                    </div>
                </div>
                <div class="content">
                    <div class="left-part">
                        <h5><fmt:message key="subscription.activity.to"/>:</h5>
                    </div>
                    <div class="right-part">
                        <h5><c:out value="${subscribtionDTO.dateTo}"/></h5>
                    </div>
                </div>
            </div>

        </c:forEach>
    </c:if>
    <c:if test="${empty subscriptionsList}">
       <h2 id="dont-have-activity-subscriptions"><c:out value="${USER_IS_UNBLOCKED.name}"/> <c:out value="${USER_IS_UNBLOCKED.patronymic}"/>,
          <fmt:message key="you.do.not.have.an.active.subscription"/> <a href="<c:out value="${pageContext.request.contextPath}"/>/media"><fmt:message key="homepage"/></a>
           <fmt:message key="and.select.the.edition"/>.</h2>
    </c:if>
</main>
<!-- footer -->
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/my-subscriptions">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit">RU</button>
                <input type="hidden" name="URLFromRequest" value="/my-subscriptions">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/my-subscriptions">
            </form>
        </div>
    </div>
</footer>
</body>
</html>
