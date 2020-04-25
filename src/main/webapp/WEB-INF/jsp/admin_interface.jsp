<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 23.02.2020
  Time: 10:21
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
    <link rel="stylesheet" type="text/css" href="css/simple_nav_bar.css">
    <link rel="stylesheet" type="text/css" href="css/admin_interface.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">

    <title>INFOLIVE:admin</title>
</head>
<body>
<tg:navbar_for_admin_and_user/>
<main>
    <div id="all-bootstrap-btn">
        <div id="create-new-media">
            <form action="create-media" method="post" enctype="multipart/form-data">
                <h3 class="header-create-media"><b><fmt:message key="create.new.media"/>↓</b></h3>
                <div id="createNewMedia">
                    <div id="leftPart">
                        <div id="media-name">
                            <h5><fmt:message key="media.name"/>:</h5>
                            <input type="text" placeholder="<fmt:message key="media.name"/>" name="mediaName"
                                   class="input-create-media">
                        </div>
                        <div id="description">
                            <br><h5><fmt:message key="description"/>:</h5>
                            <textarea rows="5" cols="80" maxlength="1000" name="description"
                                      placeholder="<fmt:message key="description"/>" class="input-create-media"
                                      id="input-description"></textarea>
                        </div>
                        <div id="price">
                            <br><h5><fmt:message key="price"/>:</h5>
                            <input type="number" name="price" placeholder="<fmt:message key="price"/>"
                                   class="input-create-media">
                        </div>
                        <div id="publication-in-month">
                            <br><h5><fmt:message key="publication.in.month"/>:</h5>
                            <input type="number" name="publicationsInMonth"
                                   placeholder="<fmt:message key="quantity.in.month"/>" class="input-create-media">
                        </div>
                        <div id="logo-img">
                            <br><h5><fmt:message key="Logo.image.name"/>:</h5>
                            <small><fmt:message key="before.create.a.new.file"/>*</small>
                            <input type="text" name="imgName" placeholder="logoImg.png" class="input-create-media">
                            <h5><fmt:message key="Upload.file"/></h5>
                            <input type="file" name="imgLogo">
                        </div>
                        <div id="pdf-file">
                            <br><h5><fmt:message key="PDF.file.name"/>:</h5>
                            <small><fmt:message key="before.create.a.new.file"/>*</small>
                            <input type="text" name="pdfName" placeholder="fileMedia.pdf" class="input-create-media">
                            <h5><fmt:message key="Upload.file"/></h5>
                            <input type="file" accept="application/pdf" name="pdfFile">
                        </div>
                    </div>
                    <div id="centralPart">
                        <h5><b><fmt:message key="IMG.LOGO.NAMES"/>:</b></h5>
                        <jsp:useBean id="IMG_LOGO_NAMES" scope="request" type="java.util.List<java.lang.String>"/>
                        <c:forEach items="${IMG_LOGO_NAMES}" var="img_name">
                            <c:out value="${img_name}"/><br>
                        </c:forEach>
                        <br><h5><b><fmt:message key="PDF.NAMES"/>:</b></h5>
                        <jsp:useBean id="PDF_NAMES" scope="request" type="java.util.List<java.lang.String>"/>
                        <c:forEach items="${PDF_NAMES}" var="pdf_name">
                            <c:out value="${pdf_name}"/><br>
                        </c:forEach>
                    </div>

                    <div id="rightPart">
                        <div id="topic">
                            <h5><b><fmt:message key="Topic"/>:</b></h5>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="UKRAINIAN">
                                    <fmt:message key="ukrainian"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="ENGLISH">
                                    <fmt:message key="english"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="RUSSIAN">
                                    <fmt:message key="russian"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="NEWS">
                                    <fmt:message key="news"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="TECHNOLOGIES">
                                    <fmt:message key="technologies"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="BUSINESS">
                                    <fmt:message key="business"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="FASHION">
                                    <fmt:message key="fashion"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="SCIENCE">
                                    <fmt:message key="science"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="SPORT">
                                    <fmt:message key="sport"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="POLITICS">
                                    <fmt:message key="politics"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="ASTROLOGY">
                                    <fmt:message key="astrology"/>
                                </label>
                            </div>
                            <div class="block-topic">
                                <label>
                                    <input type="checkbox" name="topic" value="CULINARY">
                                    <fmt:message key="cooking"/>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div id="sbmtBtn">
                        <button type="submit" name="btn_create_new_media" class="btn_submit"><fmt:message
                                key="CREATE"/></button>
                    </div>
                </div>
            </form>
        </div>
        <div id="block-unblock-user">
            <div id="blockUnblockUser">
                <div class="card card-body">
                    <form action="admin-interface" method="post">
                        <div id="block-user">
                            <h5><b><fmt:message key="Block.user.by.user.email"/>↓</b></h5>
                            <c:if test="${Email_NOT_existing_blocked != null}">
                                <jsp:useBean id="Email_NOT_existing_blocked" scope="request" type="java.lang.String"/>
                                Email <c:out value="${Email_NOT_existing_blocked}"/> doesn't existing
                            </c:if>
                            <c:if test="${UserISBlocked != null}">
                                <jsp:useBean id="UserISBlocked" scope="request" type="java.lang.String"/>
                                User with <c:out value="${UserISBlocked}"/> was blocked
                            </c:if>
                            <input type="email" name="blockUserByEmail" class="input-email-for-lock-unlock" placeholder="block_example@exm.com"
                                   pattern="^[\w!#$%&’*+/=?`{|}~^-]+(?:\.[\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$">
                            <button type="submit" name="blockUserBtn" id="btn-block"><fmt:message key="BLOCK"/></button>
                        </div>
                        <div id="unblock-user">
                            <h5><b><fmt:message key="Unblock.user.by.user.email"/>↓</b></h5>
                            <c:if test="${Email_NOT_existing_Unblocked != null}">
                                <jsp:useBean id="Email_NOT_existing_Unblocked" scope="request" type="java.lang.String"/>
                                Email <c:out value="${Email_NOT_existing_Unblocked}"/> doesn't existing
                            </c:if>
                            <c:if test="${UserISUnblocked != null}">
                                <jsp:useBean id="UserISUnblocked" scope="request" type="java.lang.String"/>
                                User with <c:out value="${UserISUnblocked}"/> was unblocked
                            </c:if>
                            <input type="email" name="unblockUserByEmail" class="input-email-for-lock-unlock" placeholder="unblock_example@exm.com"
                                   pattern="^[\w!#$%&’*+/=?`{|}~^-]+(?:\.[\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$">
                            <button type="submit" name="unblockUserBtn" id="btn-unblock"><fmt:message key="UNBLOCK"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- footer -->
<footer>
    <div id="forLocale">
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnEN" id="localeBtnEN" title="English" type="submit">EN</button>
                <input type="hidden" name="URLFromRequest" value="/admin-interface">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnRU" id="localeBtnRU" title="Русский" type="submit">RU</button>
                <input type="hidden" name="URLFromRequest" value="/admin-interface">
            </form>
        </div>
        <div class="forLocaleBtn">
            <form action="locale">
                <button name="localeBtnUK" id="localeBtnUK" title="Українська" type="submit">UK</button>
                <input type="hidden" name="URLFromRequest" value="/admin-interface">
            </form>
        </div>
    </div>
</footer>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<tg:bootstrap_tag/>
</body>
</html>
