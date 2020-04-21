<%--
  Created by IntelliJ IDEA.
  User: Ace8
  Date: 3/29/2020
  Time: 8:11 PM
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
    <link rel="stylesheet" type="text/css" href="css/update_media.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <%--  Img for title  --%>
    <link rel="shortcut icon" href="icon/letter_i.png" type="image/png">

    <title>INFOLIVE:update-media</title>
</head>
<body>
<tg:nav_bar_media_details/>

<c:if test="${mediaNameUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Media.name.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${descriptionUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Media.description.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${imgLogoUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Image.logo.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${PDFUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="PDF.file.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${topicUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Topic.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${priceUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Price.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${subscribersQuantityUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Subscribers.quantity.updated"/></b></h4>
    </div>
</c:if>
<c:if test="${publicationsQuantityUpdated != null}">
    <div class="updated-info">
        <h4><b><fmt:message key="Publications.in.month.quantity.updated"/></b></h4>
    </div>
</c:if>
<div id="content">
    <c:if test="${media != null}">
        <div id="block-with-media">
            <jsp:useBean id="media" scope="request" type="ua.nure.khshanovskyi.infoLife.entity.media.Media"/>
            <div id="img-block">
                <img id="img-logo" src="logo_imgs/<c:out value="${media.uriLogoImg}" />">
            </div>
            <div id="media-name-block">
                <h4><b><fmt:message key="media.name"/>: <b id="name-of-media"><c:out
                        value="${media.mediaName}"/></b></b></h4>
            </div>
            <div id="topics-block">
                <h5><fmt:message key="Topic"/>: <c:out value="${media.topic}"/></h5>
            </div>
            <div id="description-block">
                <h5><c:out value="${media.description}"/></h5>
            </div>
            <div id="quantity-block">
                <h5><fmt:message key="publication.in.month"/>: <c:out value="${media.quantityPublicationInMonth}"/></h5>
            </div>
            <div id="price-block-info">
                <h5><fmt:message key="price"/>: <c:out value="${media.price}"/></h5>
            </div>
            <div id="subscribers-quantity-block">
                <h5><fmt:message key="Subscribers"/>: <c:out value="${media.subscribers}"/></h5>
            </div>
        </div>
        <div id="update-block">
            <div id="update-name-block">
                <form action="update-media" method="post">
                    <fmt:message key ="Input.a.new.media.name"/>:
                    <input type="text" placeholder="<fmt:message key="a.new.media.name"/>" name="newMediaName" min="2" max="20" required>
                    <input type="hidden" name="idOfMedia" value="${media.mediaId}">
                    <button name="updateNameBtn" class="btn_submit_update"><fmt:message key="UPDATE"/></button>
                </form>
            </div>
            <div id="description-img-pdf-block">
                <form action="update-media" method="post" enctype="multipart/form-data">
                    <h5><fmt:message key="Input.a.new.description"/></h5>
                    <textarea rows="5" cols="80" maxlength="1000" name="newDescription"
                              placeholder="<fmt:message key="description"/>" id="textarea" required></textarea>
                    <br>
                    <div class="updateFilesBlock">
                        <div class="forFile">
                            <h5><fmt:message key="Logo.image.name"/>:</h5>
                            <small><fmt:message key="before.create.a.new.file"/>*</small>
                            <input type="text" name="imgName" placeholder="logoImg.png" class="input-create-media" required>
                            <h5><fmt:message key="Upload.file"/></h5>
                            <input type="file" accept="image/png image/jpeg" name="imgLogoFile" required>
                        </div>
                        <div class="forFilesInSystem">
                            <b><fmt:message key="IMG.LOGO.NAMES"/>:</b>
                            <jsp:useBean id="IMG_LOGO_NAMES" scope="request" type="java.util.List<java.lang.String>"/>
                            <c:forEach items="${IMG_LOGO_NAMES}" var="img_name">
                                <c:out value="${img_name}"/><br>
                            </c:forEach>
                        </div>
                    </div>
                    <br>
                    <div class="updateFilesBlock">
                        <div class="forFile">
                            <h5><fmt:message key="PDF.file.name"/>:</h5>
                            <small><fmt:message key="before.create.a.new.file"/>*</small>
                            <input type="text" name="pdfName" placeholder="fileMedia.pdf" class="input-create-media" required>
                            <h5><fmt:message key="Upload.file"/></h5>
                            <input type="file" accept="application/pdf" name="pdfFile" required>
                        </div>
                        <div class="forFilesInSystem">
                            <b><fmt:message key="PDF.NAMES"/>:</b>
                            <jsp:useBean id="PDF_NAMES" scope="request" type="java.util.List<java.lang.String>"/>
                            <c:forEach items="${PDF_NAMES}" var="pdf_name">
                                <c:out value="${pdf_name}"/><br>
                            </c:forEach>
                        </div>
                    </div>
                    <input type="hidden" name="idOfMedia" value="${media.mediaId}">
                    <button type="submit" name="update_main_info_media" class="btn_submit_update" value="submit">
                        <fmt:message key="UPDATE"/>
                    </button>
                </form>
            </div>
            <div id="update-topic-and-price-block">
                <div id="topic-block">
                    <form action="update-media" method="post">
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
                        <input type="hidden" name="idOfMedia" value="${media.mediaId}">
                        <button type="submit" name="updateMediaTopics" class="btn_submit_update"><fmt:message key="UPDATE"/></button>
                    </form>
                </div>
                <div id="price-and-subscribers-block-update">
                    <form action="update-media" method="post">
                        <div id="price-block">
                            <h5><b><fmt:message key="New.price"/>:</b></h5>
                            <input type="number" placeholder="<fmt:message key="New.price"/>" name="newMediaPrice" class="newMediaPrice">
                            <input type="hidden" name="idOfMedia" value="${media.mediaId}">
                            <button type="submit" name="updateMediaPrice" class="btn_submit_update"><fmt:message key="UPDATE"/></button>
                        </div>
                    </form>
                    <form action="update-media" method="post">
                        <div id="publications-block">
                            <h5><b><fmt:message key="Publications.in.month"/>:</b></h5>
                            <input type="number" placeholder="<fmt:message key="publications.quantity"/>" name="newMediaPublicationsQuantity"
                                   class="newMediaPrice">
                            <input type="hidden" name="idOfMedia" value="${media.mediaId}">
                            <button type="submit" name="updateMediaPublicationsQuantity" class="btn_submit_update">
                                <fmt:message key="UPDATE"/>
                            </button>
                        </div>
                    </form>
                    <form action="update-media" method="post">
                        <div id="subscribers-block">
                            <h5><b><fmt:message key="New.subscribers.quantity"/>:</b></h5>
                            <input type="number" placeholder="<fmt:message key="subscribers.quantity"/>" name="newMediaSubscribers"
                                   class="newMediaPrice">
                            <input type="hidden" name="idOfMedia" value="${media.mediaId}">
                            <button type="submit" name="updateMediaSubscribers" class="btn_submit_update"><fmt:message key="UPDATE"/>
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </c:if>
    <c:if test="${media == null}">
        Input id for update
        <form action="update-media" method="get">
            <input type="number" placeholder="media id" name="update_media_id" class="newMediaPrice">
            <button type="submit" name="updateMediaSubscribers" class="btn_submit_update"><fmt:message key="UPDATE"/></button>
        </form>
    </c:if>
</div>

</body>
</html>
