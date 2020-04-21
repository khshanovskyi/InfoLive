<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="${bundle}"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/blocked_user_page.css">
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">

    <title>INFOLIVE: <fmt:message key="BLOCKED"/></title>
</head>
<body>
<h1><fmt:message key="message.for.blocked.user.header1"/></h1>
<h2><fmt:message key="message.for.blocked.user.header2"/></h2>
<h2>0932269758</h2>
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/BLOCKED">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit"}>RU</button>
                <input type="hidden" name="URLFromRequest" value="/BLOCKED">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/BLOCKED">
            </form>
        </div>
    </div>
</footer>
</body>
</html>
