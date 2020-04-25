<jsp:useBean id="MediaObj" scope="request" type="ua.nure.khshanovskyi.infoLife.entity.media.Media"/>
<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/18/2020
  Time: 8:02 PM
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
    <link rel="stylesheet" type="text/css" href="css/details.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">
    <title>INFOLIVE: <fmt:message key="product.info"/></title>
</head>
<body>
<%--NavBar Menu--%>
<tg:nav_bar_media_details/>
<%--main--%>
<main>
    <div id="picture-block">
        <img id="media-logo-img" src="logo_imgs/<c:out value="${MediaObj.uriLogoImg}"/>" alt="logo">
    </div>
    <div id="subscription-and-info">
        <div id="with-name">
            <h2 id="name-of-media-header"><b>«<c:out value="${MediaObj.mediaName}"/>»</b></h2>
        </div>
        <div id="description" class="for-margin">
            <c:out value="${MediaObj.description}"/>
        </div>
        <div id="update-time" class="for-margin">
            <fmt:message key="update.date"/>: <c:out value="${MediaObj.date}"/> / <c:out value="${MediaObj.time}"/>
        </div>
        <div id="read" class="for-margin">
            <div id="with-href-to-pdf-in-img">
                <a href="reader?read-online=<c:out value="${MediaObj.pdfUri}"/>" target="popup"><img id="user-read"
                                                                                                     src="icon/study.png" alt="read"></a>
            </div>
            <div id="with-href-to-pdf">
                <a href="reader?read-online=<c:out value="${MediaObj.pdfUri}"/>" target="popup"><h4><b><fmt:message
                        key="read"/> <c:out value="${MediaObj.mediaName}"/> <fmt:message key="online"/></b></h4></a>
            </div>
        </div>
        <div id="subscribing" class="for-margin">
            <c:if test="${SUBSCRIPTION == null}">
                <h4><fmt:message key="Please.support.us"/> <b id="media-name-in-subscribing">«<c:out
                        value="${MediaObj.mediaName}"/>»</b>
                    <fmt:message key="print.subscription"/>.</h4>
                <p>
                    <fmt:message key="By.subscribing.you.can.view.the.edition.archive"/> «<c:out
                        value="${MediaObj.mediaName}"/>»,
                    <fmt:message key="you.will.receive"/> ${MediaObj.quantityPublicationInMonth}
                    <c:if test="${MediaObj.quantityPublicationInMonth == 1}">
                        <fmt:message key="print.edition"/>
                    </c:if>
                    <c:if test="${MediaObj.quantityPublicationInMonth > 1}">
                        <fmt:message key="printed.publications"/>
                    </c:if> <fmt:message key="in.month"/>.<br>
                    <fmt:message key="The.ost.of.a.month.subscription.is"/>: <c:out value="${MediaObj.price}"/>
                    <fmt:message
                            key="grn"/>.
                </p>
                <form action="subscribing">
                    <b class="radio-subscription-period"><label>
                        <input name="subscription-period" type="radio" value="one_month">
                        1 <fmt:message key="month"/>/<fmt:message key="cost"/>: ${MediaObj.price} <fmt:message
                            key="grn"/>
                    </label><br></b>

                    <b class="radio-subscription-period"><label>
                        <input name="subscription-period" type="radio" value="three_months">
                        3 <fmt:message key="months"/>/<fmt:message key="cost"/>: ${MediaObj.price * 3} <fmt:message
                            key="grn"/>
                    </label><br></b>

                    <b class="radio-subscription-period"><label>
                        <input name="subscription-period" type="radio" value="six_months">
                        6 <fmt:message key="months"/>/<fmt:message key="cost"/>: ${MediaObj.price * 6} <fmt:message
                            key="grn"/>
                    </label><br></b>

                    <b class="radio-subscription-period"><label>
                        <input name="subscription-period" type="radio" value="one_year" checked>
                        1 <fmt:message key="year"/>/<fmt:message key="cost"/>: ${MediaObj.price * 12} <fmt:message
                            key="grn"/>
                    </label><br></b>

                    <button type="submit" name="subscription" id="subscription-btn">
                        <b><fmt:message key="SUBSCRIBE"/></b>
                    </button>
                    <input type="hidden" name="media_id" value="${MediaObj.mediaId}">
                </form>
            </c:if>
            <c:if test="${SUBSCRIPTION != null}">
                <jsp:useBean id="SUBSCRIPTION" scope="request"
                             type="ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription"/>
                <h3><fmt:message key="Thank.you.for.subscribing.on.the.print.edition"/>.<br>
                    <fmt:message key="Your.subscription.is.active.until"/><b> <c:out
                            value="${SUBSCRIPTION.dateTo}"/></b></h3>.
            </c:if>
            <c:if test="${USER_IS_UNBLOCKED != null}">
                <jsp:useBean id="USER_IS_UNBLOCKED" scope="session"
                             type="ua.nure.khshanovskyi.infoLife.entity.user.User"/>
                <c:if test="${USER_IS_UNBLOCKED.role.equals('ADMIN')}">
                    <div id="updateMedia">
                        <form action="update-media">
                            <h3 class="header-create-media"><fmt:message key="update"/> <b
                                    class="media-name-bold">«<c:out value="${MediaObj.mediaName}"/>»</b></h3>
                            <input type="hidden" name="update_media_id" value="${MediaObj.mediaId}">
                            <button type="submit" name="btn_to_update" class="btn_submit"><fmt:message
                                    key="UPDATE"/></button>
                        </form>
                    </div>

                    <div id="deleteMedia">
                        <div class="card card-body">
                            <form action="delete-media" method="post">
                                <h3 class="header-create-media"><fmt:message key="delete"/> <b class="media-name-bold">«<c:out
                                        value="${MediaObj.mediaName}"/>»</b></h3>
                                <input type="hidden" name="deleteMediaID" value="${MediaObj.mediaId}">
                                <button type="submit" name="deleteMediaBtn" id="btn_delete" class="btn_submit">
                                    <fmt:message key="DELETE"/></button>
                            </form>
                        </div>
                    </div>

                </c:if>
            </c:if>
        </div>
    </div>
</main>
<%--FOOTER--%>
<tg:footer/>
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
