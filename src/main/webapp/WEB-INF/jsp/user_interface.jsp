<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 22.02.2020
  Time: 22:05
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
    <link rel="stylesheet" type="text/css" href="css/simple_nav_bar.css">
    <link rel="stylesheet" type="text/css" href="css/user_interface.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">

    <title>INFOLIVE: <fmt:message key="user.interface.icon.in.header"/></title>
</head>

<body>
<!-- navbar menu -->
<tg:navbar_for_admin_and_user/>
<!-- main section with order details -->
<jsp:useBean id="USER_IS_UNBLOCKED" scope="session" type="ua.nure.khshanovskyi.infoLife.entity.user.User"/>
<%--INFO AND UPDATING USER MONEY--%>
<div id="content1">
    <div id="user-all-info">
        <h2 id="about-me"><fmt:message key="about.me"/>:</h2>
        <div class="topic">
            <h5><fmt:message key="full.name"/>:</h5>
            <h5>e-mail:</h5>
            <h5><fmt:message key="phone.number"/>:</h5>
            <h5><fmt:message key="region"/>:</h5>
            <c:if test="${not empty USER_IS_UNBLOCKED.district}"><h5><fmt:message key="district"/>:</h5></c:if>
            <h5><fmt:message key="locality"/>:</h5>
            <h5><fmt:message key="street"/>:</h5>
            <h5><fmt:message key="house"/>:</h5>
            <c:if test="${USER_IS_UNBLOCKED.flat != 0}"><h5><fmt:message key="flat"/>:</h5></c:if>
            <h5><fmt:message key="postcode"/>:</h5>
        </div>
        <div class="actual-info">
            <h5><c:out value="${USER_IS_UNBLOCKED.surname}"/> <c:out value="${USER_IS_UNBLOCKED.name}"/> <c:out value="${USER_IS_UNBLOCKED.patronymic}"/></h5>
            <h5> <c:out value="${USER_IS_UNBLOCKED.email}"/></h5>
            <h5>0<c:out value="${USER_IS_UNBLOCKED.phoneNumber}"/></h5>
            <h5><c:out value="${USER_IS_UNBLOCKED.region}"/></h5>
            <c:if test="${not empty USER_IS_UNBLOCKED.district}"><h5><c:out value="${USER_IS_UNBLOCKED.district}"/></h5></c:if>
            <h5><c:out value="${USER_IS_UNBLOCKED.locality}"/></h5>
            <h5><c:out value="${USER_IS_UNBLOCKED.street}"/></h5>
            <h5><c:out value="${USER_IS_UNBLOCKED.house}"/></h5>
            <c:if test="${USER_IS_UNBLOCKED.flat != 0}"><h5><c:out value="${USER_IS_UNBLOCKED.flat}"/></h5></c:if>
            <h5><c:out value="${USER_IS_UNBLOCKED.postcode}"/></h5>
        </div>
    </div>
    <div id="money-and-subscribing-href">
        <h2 id="money-and-subscribing-header"><fmt:message key="Money.and.subscribing"/>:</h2>
        <h3><fmt:message key="My.money.amount"/>: <b>₴</b> <c:out value="${USER_IS_UNBLOCKED.money}"/></h3>
        <div id="withPayHref">
            <a class="userBtnPay" href="/InfoLive.ua/payment-service" title="<fmt:message key="add.money.title"/>">
                <img class="userPayImg"src="icon/currency.png"></a>
        </div>
        <h3><a href="/InfoLive.ua/payment-service"><fmt:message key="add.money"/></a></h3>
        <h3 id="my-subscribing-href"><a href="/InfoLive.ua/my-subscriptions" title="<fmt:message key="my.subscribing.details"/>"><fmt:message key="my.subscribing"/> →</a></h3>
    </div>
</div>
<%--FIELDS WITH UPDATE SETTINDS--%>
<div id="content">
    <div class="with-setting">
        <div class="dropdown-with-settings">
            <h3 class="href-with-info-for-changing"><fmt:message key="change.e.mail"/></h3>
            <b><fmt:message key="your.email.is"/> <c:out value="${USER_IS_UNBLOCKED.email}"/></b>
            <form action="user-interface" method="post" class="form-for-change-data">
                <input type="email" class="inputField" name="E_MAIL" id="change-email" required max="30"
                       placeholder="<fmt:message key="input.new.email"/>"
                       pattern="^[\w!#$%&’*+/=?`{|}~^-]+(?:\.[\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$">
                <input class="inputField" type="password" name="PASSWORD_CONFIRM" required max="21"
                       placeholder="<fmt:message key="password.placeholder"/>"
                       pattern="[A-Za-z0-9]{6,21}">
                <button type="submit" name="SUBMIT_CHANGE_EMAIL" class="submit-in-interface">
                    <fmt:message key="btn.submit.in.user.interface"/></button>
            </form>
        </div>
        <div class="dropdown-with-settings">
            <div class="dropdown">
                <h3 class="href-with-info-for-changing"><fmt:message key="change.phone.number"/></h3>
                <b><fmt:message key="your.phone.number"/> 0${USER_IS_UNBLOCKED.phoneNumber}</b>
                <form action="user-interface" method="post" class="form-for-change-data">
                    <input class="inputField" type="text" name="CHANGE_PHONE_NUM" id="change-phone-num"
                           placeholder="<fmt:message key="input.new.phone.number"/>"
                           pattern="[0-9]{10,12}" required max="12">
                    <button type="submit" name="SUBMIT_CHANGE_PHONE_NUM" class="submit-in-interface">
                        <fmt:message key="btn.submit.in.user.interface"/></button>
                </form>
            </div>
        </div>
        <div class="dropdown-with-settings">
            <h3 class="href-with-info-for-changing"><fmt:message key="change.password"/></h3>
            <form action="user-interface" method="post" class="form-for-change-data">
                <input class="inputField" type="password" name="OLD_PASSWORD" required max="21"
                       placeholder="<fmt:message key="your.old.password"/>"
                       pattern="[A-Za-z0-9]{8,21}">
                <input class="inputField" type="password" name="NEW_PASSWORD" required max="21"
                       placeholder="<fmt:message key="input.new.password"/>"
                       pattern="[A-Za-z0-9]{8,21}">
                <input class="inputField" type="password" name="NEW_PASSWORD_CONFIRM" required max="21"
                       placeholder="<fmt:message key="confirm.new.password"/>"
                       pattern="[A-Za-z0-9]{8,21}">
                <button type="submit" name="SUBMIT_CHANGE_PASSWORD" class="submit-in-interface">
                    <fmt:message key="btn.submit.in.user.interface"/></button>
            </form>
        </div>
        <div class="dropdown-with-settings">
            <h3 class="href-with-info-for-changing"><fmt:message key="change.name.surname"/></h3>
            <b><c:out value="${USER_IS_UNBLOCKED.surname}"/> <c:out value="${USER_IS_UNBLOCKED.name}"/> <c:out
                    value="${USER_IS_UNBLOCKED.patronymic}"/>,</b>
            <fmt:message key="info.about.name.surname"/>
            <form action="user-interface" method="post" class="form-for-change-data">
                <input class="inputField" name="CHANGE_SURNAME" type="text" tabindex="7"
                       placeholder="<fmt:message key="second.name.title.registration"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ-]{2,15}" max="15">
                <input class="inputField" name="CHANGE_NAME" type="text" tabindex="6"
                       placeholder="<fmt:message key="first.name.title.registration"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ-]{2,15}" max="15">
                <input class="inputField" name="CHANGE_PATRONYMIC" type="text" tabindex="8"
                       placeholder="<fmt:message key="your.patronymic"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ-]{2,15}" max="15">
                <button type="submit" name="SUBMIT_CHANGE_NAME_SURNAME" class="submit-in-interface">
                    <fmt:message key="btn.submit.in.user.interface"/></button>
            </form>
        </div>
        <div class="dropdown-with-settings">
            <h3 class="href-with-info-for-changing"><fmt:message key="change.address.header"/></h3>
            <fmt:message key="please.input.valid.data"/>
            <form action="user-interface" method="post" class="form-for-change-data">
                <b><fmt:message key="region"/>:</b>
                <input class="inputField" name="CHANGE_REGION" type="text" max="20"
                       placeholder="<c:out value="${USER_IS_UNBLOCKED.region}"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
                <b><fmt:message key="district"/>:</b>
                <input class="inputField" name="CHANGE_DISTRICT" type="text" max="20"
                       placeholder="<c:out value="${USER_IS_UNBLOCKED.district}"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
                <b><fmt:message key="locality"/>:</b>
                <input class="inputField" name="CHANGE_LOCALITY" type="text" max="20"
                       placeholder="<c:out value="${USER_IS_UNBLOCKED.locality}"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
                <b><fmt:message key="street"/>:</b>
                <input class="inputField" name="CHANGE_STREET" type="text" max="30"
                       placeholder="<c:out value="${USER_IS_UNBLOCKED.street}"/>"
                       pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
                <b><fmt:message key="house"/>:</b>
                <input class="inputField" name="CHANGE_HOUSE" type="text" max="5"
                       placeholder="<c:out value="${USER_IS_UNBLOCKED.house}"/>" pattern="[0-9/]{1,5}">
                <b><fmt:message key="flat"/>:</b>
                <input class="inputField" name="CHANGE_FLAT" type="text" max="3"
                       placeholder="<c:if test="${USER_IS_UNBLOCKED.flat  == 0}"><fmt:message key="your.flat"/></c:if><c:if test="${USER_IS_UNBLOCKED.flat  != 0}"><c:out value="${USER_IS_UNBLOCKED.flat}"/></c:if>"
                       pattern="[0-9]{1,3}">
                <b><fmt:message key="your.postcode"/>:</b>
                <input class="inputField" name="CHANGE_POSTCODE" type="text" min="4" max="6"
                       placeholder="<c:out value="${USER_IS_UNBLOCKED.postcode}"/>" pattern="[0-9]{4,6}">

                <button type="submit" name="SUBMIT_CHANGE_ADDRESS" class="submit-in-interface">
                    <fmt:message key="btn.submit.in.user.interface"/></button>
            </form>
        </div>
        <div id="logout-div">
            <form action="logout" method="post">
                <button class="logoutBtn" name="LOGOUT" title="<fmt:message key="logout.btn"/>">
                    <fmt:message key="logout.btn"/></button>
            </form>
        </div>
    </div>
</div>
<!-- footer -->
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/user-interface">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit" }>RU</button>
                <input type="hidden" name="URLFromRequest" value="/user-interface">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/user-interface">
            </form>
        </div>
    </div>
</footer>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<tg:bootstrap_tag/>
</body>
</html>
