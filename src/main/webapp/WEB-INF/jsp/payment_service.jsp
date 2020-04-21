<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/20/2020
  Time: 9:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/nav_bar_with_search_field.css">
    <link rel="stylesheet" type="text/css" href="css/payment_service.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">
    <title>INFOLIVE:<fmt:message key="pay"/></title>
</head>
<body>
<%--NavBar Menu--%>
<tg:nav_bar_media_details/>

<jsp:useBean id="USER_IS_UNBLOCKED" scope="session" type="ua.nure.khshanovskyi.infoLife.entity.user.User"/>
<c:if test="${SUBSCRIPTION_DTO != null}">

    <jsp:useBean id="SUBSCRIPTION_DTO" scope="session" type="ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionDTO"/>
    <div id="info-about-money">
        <h3><fmt:message key="Sorry.You.have.not.enough.money"/> <b>${USER_IS_UNBLOCKED.money} <fmt:message key="grn"/></b>
            <fmt:message key="in.your.account.You.need.to.pay"/>
            <b><c:out value="${SUBSCRIPTION_DTO.pricePerPeriod - USER_IS_UNBLOCKED.money}"/> <fmt:message key="grn"/></b>
            <fmt:message key="for.subscribing"/>.
        </h3>
    </div>
</c:if>
<form action="payment-service" method="post">
    <div id="field-for-payment">
        <div id="money-quantity">
            <c:if test="${SUBSCRIPTION_DTO != null}">
                <div id="payment">
                    <h4><fmt:message key="Payment"/> <b><c:out value="${SUBSCRIPTION_DTO.pricePerPeriod - USER_IS_UNBLOCKED.money}"/>
                        <fmt:message key="grn"/></b> <fmt:message key="for.subscribing"/>.</h4>
                    <input type="hidden" name="money_amount"
                           value="<c:out value="${SUBSCRIPTION_DTO.pricePerPeriod - USER_IS_UNBLOCKED.money}"/>">
                </div>
            </c:if>
            <c:if test="${SUBSCRIPTION_DTO == null}">
                <input id="input-for-money" type="text" pattern="[0-9]{1,5}" max="5" required
                       title="<fmt:message key="enter.the.recharge.amount"/>"  name="money_amount"
                       placeholder="<fmt:message key="enter.the.recharge.amount"/>">
            </c:if>
        </div>
        <div id="cart">
            <div id="cart-number">
                <input type="text" id="cart-num" pattern="[0-9]{16}" placeholder="<fmt:message key="CARD.NUMBER"/>" min="16"
                       title="16 numbers" required name="card_number">
                <small><fmt:message key="Enter.your.card.number"/> <br> <fmt:message key="in.the.format"/> 0000 0000
                    0000 0000</small>
            </div>
            <div id="cvv-and-period">
                <div id="date-activity-cart">
                    <input type="number" id="year" placeholder="YYYY" pattern="[0-9]{4}" required name="year"  min="2020" max="2030">
                    <b id="slash">/</b>
                    <input type="number" id="month" placeholder="MM" pattern="[0-9]{1,2}" required name="month" min="01" max="12">
                    <small><fmt:message key="Please.enter.a.year"/> (YYYY) <fmt:message key="and.month"/> (MM)
                        <fmt:message key="card.activity"/>. <fmt:message key="This.information.is.placed"/>.</small>
                </div>
                <div id="cvv">
                    <input type="text" id="cvv-code" pattern="[0-9]{3,4}" min="3" max="4" required placeholder="CVV" name="CVV-code">
                    <small><fmt:message key="Enter.the.CVV.code"/></small>
                </div>
            </div>
        </div>
        <button type="submit" name="pay-button" id="btn-pay-submit">
            <fmt:message key="PAY"/>
        </button>
    </div>
</form>

<!-- footer -->
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/payment-service">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit" }>RU</button>
                <input type="hidden" name="URLFromRequest" value="/payment-service">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/payment-service">
            </form>
        </div>
    </div>
</footer>
<tg:bootstrap_tag/>
</body>
</html>
