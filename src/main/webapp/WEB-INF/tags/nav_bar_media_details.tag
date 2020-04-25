<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="${bundle}"/>
<nav>
    <div class="navbarMenu">
        <div class="leftPartNavbar">
            <div class="brandNameField">
                <a class="navbar-brand" href="<c:out value="${pageContext.request.contextPath}"/>/media" title="<fmt:message key="home.page"/>">
                    <strong id="infoLifeLabel">INFOLIVE</strong></a>
            </div>
            <div class="LookingForMenu">
                <form action="media">
                    <input type="text" class="searchField" name="request"
                           placeholder="<fmt:message key="i.am.looking.for"/>">
                    <button type="submit" class="searchButton" name="search"><fmt:message key="search.btn"/></button>
                </form>
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
                <c:choose>
                    <c:when test="${USER_IS_UNBLOCKED != null}">
                        <jsp:useBean id="USER_IS_UNBLOCKED" scope="session" type="ua.nure.khshanovskyi.infoLife.entity.user.User"/>
                        <c:if test="${USER_IS_UNBLOCKED.role.equals('USER')}">
                            <a class="userLoginBtn" href="<c:out value="${pageContext.request.contextPath}"/>/distributor" title="<fmt:message key="user.setting"/>">
                                <img class="userLoginImg" src="icon/user.png" alt="login"></a>
                        </c:if>
                        <c:if test="${USER_IS_UNBLOCKED.role.equals('ADMIN')}">
                            <a class="userLoginBtn" href="<c:out value="${pageContext.request.contextPath}"/>/distributor" title="<fmt:message key="user.setting"/>">
                                <img class="userLoginImg" src="icon/admin.png" alt="login"></a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <a class="userLoginBtn" href="<c:out value="${pageContext.request.contextPath}"/>/login" title="<fmt:message key="Login"/>">
                            <img class="userLoginImg" src="icon/login.png" alt="login"></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>