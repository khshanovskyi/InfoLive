<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- navbar menu -->
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
                <form action="logout" method="post">
                    <button class="userLoginBtn" name="LOGOUT" title="<fmt:message key="logout.btn"/>">
                        <img class="userLoginImg" src="icon/logout.png" alt="login"></button>
                </form>
            </div>
        </div>
    </div>
</nav>
<!-- main section with order details -->