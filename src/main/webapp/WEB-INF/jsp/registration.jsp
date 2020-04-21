<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <%--My Style--%>
    <link rel="stylesheet" type="text/css" href="css/simple_nav_bar.css">
    <link rel="stylesheet" type="text/css" href="css/registration.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">

    <title>INFOLIVE: <fmt:message key="registration"/></title>
</head>
<body>
<!-- header -->
<nav>
    <div class="navbarMenu">
        <div class="leftPartNavbar">
            <div class="brandNameField">
                <a class="navbar-brand" href="/InfoLive.ua/media" title="<fmt:message key="home.page"/>">
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
                <a class="userLoginBtn" href="/InfoLive.ua/media" title="<fmt:message key="Login"/>">
                    <img class="userLoginImg"src="icon/login.png"></a>
            </div>
        </div>
    </div>
</nav>
<!-- main part -->
<main>
    <div id="loginOutsideBorder">
        <div>
            <h3><fmt:message key="header.info.for.user.in.registration"/></h3>
            <h4 id="requiredField"><fmt:message key="info.required.fields"/><b>*</b></h4>
        </div>
        <form method="post" action="registration-controller">
            <div class="fieldDiv">
                <h2><fmt:message key="email.header"/>:<b>*</b></h2>
                <input class="inputField" type="email" name="REGISTRATION_EMAIL" required
                       placeholder="<fmt:message key="email.title.registration"/>*" max="35"
                       pattern="^[\w!#$%&’*+/=?`{|}~^-]+(?:\.[\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="second.name.header"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_SURNAME" type="text" required max="15"
                       placeholder="<fmt:message key="second.name.title.registration"/>*" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ-]{2,15}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="first.name.header"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_NAME" type="text" required max="15"
                       placeholder="<fmt:message key="first.name.title.registration"/>*" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ-]{2,15}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="patronymic"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_PATRONYMIC" type="text" required max="15"
                       placeholder="<fmt:message key="your.patronymic"/>*" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ-]{2,15}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="PN"/>:<b>*</b></h2>
                <input class="inputField" type="text" name="REGISTRATION_PHONE_NUMBER" required
                       placeholder="<fmt:message key="PNHeader"/>*" pattern="[0-9]{10,12}" max="12">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="region"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_REGION" type="text" required max="20"
                       placeholder="<fmt:message key="your.region"/>" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="district"/>:</h2>
                <input class="inputField" name="REGISTRATION_DISTRICT" type="text" max="20"
                       placeholder="<fmt:message key="your.district"/>" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="locality"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_LOCALITY" type="text" required max="20"
                       placeholder="<fmt:message key="your.locality"/>*" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="street"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_STREET" type="text" required max="30"
                       placeholder="<fmt:message key="your.street"/>" pattern="[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 \\.-]{2,30}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="house"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_HOUSE" type="text" required max="5"
                       placeholder="<fmt:message key="your.house"/>" pattern="[0-9/]{1,5}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="flat"/>:</h2>
                <input class="inputField" name="REGISTRATION_FLAT" type="text" max="3"
                       placeholder="<fmt:message key="your.flat"/>" pattern="[0-9]{1,3}">
            </div>
            <div class="fieldDiv">
                <h2><fmt:message key="your.postcode"/>:<b>*</b></h2>
                <input class="inputField" name="REGISTRATION_POSTCODE" type="text" required min="4" max="6"
                       placeholder="<fmt:message key="second.name.title.registration"/>" pattern="[0-9]{4,6}">
            </div>
            <div>
                <h2><fmt:message key="password.header"/>:<b>*</b></h2>
                <input class="inputField" type="password" name="REGISTRATION_PASSWORD" placeholder="<fmt:message key="password.placeholder"/>"
                       pattern="[A-Za-z0-9]{6,21}" required  max="21">
            </div>
            <div>
                <h2><fmt:message key="confirm.password"/>:<b>*</b></h2>
                <input class="inputField" type="password" name="REGISTRATION_PASSWORD_CONFIRM"
                       placeholder="<fmt:message key="confirm.password"/>"
                       pattern="[A-Za-z0-9]{6,21}" required  max="21">
            </div>
            <button id="loginBtn" type="submit" title="Login"><fmt:message key="btn.submit"/></button>
        </form>
        <a href="/InfoLive.ua/login"><fmt:message key="ref.to.login"/></a>
    </div>
</main>
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/registration">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit">RU</button>
                <input type="hidden" name="URLFromRequest" value="/registration">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/registration">
            </form>
        </div>
    </div>
</footer>
</body>
</html>