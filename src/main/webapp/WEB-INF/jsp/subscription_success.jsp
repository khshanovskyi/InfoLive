<jsp:useBean id="SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO" scope="request" type="ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO"/>
<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/20/2020
  Time: 9:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>

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
    <link rel="stylesheet" type="text/css" href="css/subscription_success.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">
    <title>INFOLIVE:<fmt:message key="success"/></title>
</head>
<body>
<%--nav bar--%>
<tg:nav_bar_media_details/>
<%--MAIN--%>
<main>
    <div id="info">
        <h4><fmt:message key="Congratulations"/>, <c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.name}"/> <c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.patronymic}"/>!
            <fmt:message key="Subscribe.to"/> <b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.mediaName}"/></b> <fmt:message key="decorated.Subscription.expires"/>
            <b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.dateTo}"/></b></h4>
        <fmt:message key="You.will.receive"/> <c:if test="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.quantityPublicationInMonth == 1}">${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.quantityPublicationInMonth} <fmt:message key="time"/></c:if>
        <c:if test="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.quantityPublicationInMonth > 1}">${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.quantityPublicationInMonth} <fmt:message key="times"/></c:if> <fmt:message key="editions.to.your.inbox"/>.
        <h5>
            <b><fmt:message key="Before.you.close.the.window"/>.
                <fmt:message key="Please.note.that.incorrect"/>!</b>
        </h5>
        <h4><fmt:message key="Please.check.your.details"/> <a href="/InfoLive.ua/distributor"><fmt:message key="personal.office"/></a> <fmt:message key="to.update.the.data"/>!</h4>
    </div>
    <div id="personal-user-info">
        <div id="name-of-topic">
            <h4 class="info-header-info"><b><fmt:message key="full.name"/>→</b></h4>
            <h4 class="info-header-info"><b><fmt:message key="phone.num"/>→</b></h4>
            <h4 class="info-header-info"><b><fmt:message key="e.mail"/>→</b></h4>
            <h4 class="info-header-info"><b><fmt:message key="region"/>→</b></h4>
            <c:if test="${not empty SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.district}"><h4 class="info-header-info"><b><fmt:message key="district"/>→</b></h4></c:if>
            <h4 class="info-header-info"><b><fmt:message key="locality"/>→</b></h4>
            <h4 class="info-header-info"><b><fmt:message key="street"/>→</b></h4>
            <h4 class="info-header-info"><b><fmt:message key="house"/>→</b></h4>
            <c:if test="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.flat != 0}"><h4 class="info-header-info"><b><fmt:message key="flat"/>→</b></h4></c:if>
            <h4 class="info-header-info"><b><fmt:message key="postcode"/>→</b></h4>
        </div>
        <div id="personal-info">
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.surname}"/> <c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.name}"/> <c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.patronymic}"/></b></h4>
            <h4 class="info-header-info"><b>0<c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.phoneNumber}"/></b></h4>
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.email}"/></b></h4>
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.region}"/></b></h4>
            <c:if test="${not empty SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.district}"><h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.district}"/></b></h4></c:if>
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.locality}"/></b></h4>
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.street}"/></b></h4>
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.house}"/></b></h4>
            <c:if test="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.flat != 0}"><h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.flat}"/></b></h4></c:if>
            <h4 class="info-header-info"><b><c:out value="${SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO.postcode}"/></b></h4>
        </div>
    </div>
</main>
<!-- footer -->
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/subscription-success">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit" }>RU</button>
                <input type="hidden" name="URLFromRequest" value="/subscription-success">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/subscription-success">
            </form>
        </div>
    </div>
</footer>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>
