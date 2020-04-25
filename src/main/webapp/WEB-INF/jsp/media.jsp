
<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/12/2020
  Time: 2:08 PM
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
    <link rel="stylesheet" type="text/css" href="css/media.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">
    <title>INFOLIVE: <fmt:message key="header.media.page"/></title>
</head>

<body>
<%--NavBar Menu--%>
<tg:nav_bar_media_details/>
<div class="content">
    <aside>
        <!-- fast sorting -->
        <div id="columnNameForFastSort">
            <h3>
                <fmt:message key="sorting"/>
            </h3>
        </div>
        <div class="fastSorting">
            <div class="fromExpensiveToCheap">
                <form action="media">
                    <button type="submit" class="fastSortButton" name="sort-popular"><b><fmt:message key="popular"/></b></button>
                </form>
            </div>
            <div class="fromExpensiveToCheap">
                <form action="media">
                    <button type="submit" class="fastSortButton" name="only-Ukrainian"><b><fmt:message key="ukrainian"/></b></button>
                </form>
            </div>
            <div class="fromExpensiveToCheap">
                <form action="media">
                    <button type="submit" class="fastSortButton" name="only-Russian"><b><fmt:message key="russian"/></b></button>
                </form>
            </div>
            <div class="fromExpensiveToCheap">
                <form action="media">
                    <button type="submit" class="fastSortButton" name="only-English"><b><fmt:message key="english"/></b></button>
                </form>
            </div>
            <div class="fromExpensiveToCheap">
                <form action="media">
                    <button type="submit" class="fastSortButton" name="sort-by-name"><b><fmt:message key="by.name"/></b></button>
                </form>
            </div>
            <div class="name">
                <form action="media">
                    <button type="submit" class="fastSortButton" name="sort-cheap"><b><fmt:message key="cheap"/></b></button>
                </form>
            </div>
            <div class="fromCheapToExpensive">
                <form action="">
                    <button type="submit" class="fastSortButton" name="sort-expensive"><b><fmt:message key="expensive"/></b></button>
                </form>
            </div>
        </div>
        <!-- advanced search-->
        <div class="advancedSearch">
            <form name="advancedSearchForm" action="media">
                <div id="advancedSearchHeader">
                    <h4>
                        <fmt:message key="sort.by.topic"/>
                    </h4>
                </div>
                <div class="checkboxes">
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                          value="ASTROLOGY"> <fmt:message key="astrology"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                          value="BUSINESS"> <fmt:message key="business"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                          value="CULINARY"> <fmt:message key="cooking"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                          value="FASHION"> <fmt:message key="fashion"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                          value="NEWS"> <fmt:message key="news"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                          value="SCIENCE"> <fmt:message key="science"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" class="checkboxAll" name="topic"
                                                                           value="POLITICS"> <fmt:message key="politics"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic"
                                                          class="checkboxAll"
                                                          value="SPORT"> <fmt:message key="sport"/></label>
                    </div>
                    <div class="allBrands">
                        <label class="labelForAll"><input type="checkbox" name="topic" class="checkboxAll"
                                                                  value="TECHNOLOGIES"> <fmt:message key="technologies"/></label>
                    </div>
                </div>

                <div id="fieldForSearchButton">
                    <button id="searchBtnInAside" type="submit" name="search-by-topic"><fmt:message key="search.btn"/></button>
                </div>
            </form>
        </div>
    </aside>
    <!-- main section with products -->
    <main>
        <!-- ICON -->
        <c:if test="${mediaList != null}">
        <jsp:useBean id="mediaList" scope="request" type="java.util.List<ua.nure.khshanovskyi.infoLife.entity.media.Media>"/>
            <c:forEach var="media" items="${mediaList}">
                <div class="icon">
                    <div class="logo-img">
                            <a href="details?info-about-media=${media.mediaId}"  title="<fmt:message key="tap.for.details"/>">
                                <img class="media-logo-img" src="logo_imgs/<c:out value="${media.uriLogoImg}"/>" alt="logo">
                            </a>
                    </div>
                    <div class="short-description">
                        <h4 class="name-of-media-header"><b>«<c:out value="${media.mediaName}"/>»</b></h4>
                        <fmt:message key="update.date"/>: <c:out value="${media.date}"/>
                        <br><fmt:message key="Frequency.of.editions.per.month"/>: ${media.quantityPublicationInMonth}
                        <c:if test="${USER_IS_UNBLOCKED.role.equals('ADMIN')}">
                            <h5>id: <c:out value="${media.mediaId}"/></h5>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${mediaList == null}">
            <c:if test="${mediaSet != null}">
            <jsp:useBean id="mediaSet" scope="request" type="java.util.Set<ua.nure.khshanovskyi.infoLife.entity.media.Media>"/>
                <c:forEach var="media" items="${mediaSet}">
                    <div class="icon">
                        <div class="logo-img">
                            <a href="details?info-about-media=${media.mediaId}"  title="<fmt:message key="tap.for.details"/>">
                                <img class="media-logo-img" src="logo_imgs/<c:out value="${media.uriLogoImg}"/>" alt="logo">
                            </a>
                        </div>
                        <div class="short-description">
                            <h4 class="name-of-media-header"><b>«<c:out value="${media.mediaName}"/>»</b></h4>
                            <fmt:message key="update.date"/>: <c:out value="${media.date}"/>
                            <br><fmt:message key="Frequency.of.editions.per.month"/>: ${media.quantityPublicationInMonth}
                            <c:if test="${USER_IS_UNBLOCKED.role.equals('ADMIN')}">
                                <h5>id: <c:out value="${media.mediaId}"/></h5>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${mediaList == null && mediaSet == null}">
                <fmt:message key="empty.product.list"/>
            </c:if>
        </c:if>
    </main>
</div>
<%--footer--%>
<tg:footer/>
<tg:bootstrap_tag/>
</body>
</html>
