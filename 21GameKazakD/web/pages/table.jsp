<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="admin" uri="/WEB-INF/taglib.tld" %>
<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 06.02.2017
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en_EN'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="configuration/lang/text"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Стол</title>
    <link rel="stylesheet" href="../css/table.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="../css/menu.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="../css/messages.css" media="screen" type="text/css"/>
    <link href="https://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic" rel="stylesheet">
    <script type="text/javascript" src="../scripts/table.js"></script>
    <script type="text/javascript" src="../scripts/menu.js"></script>
    <script type="text/javascript" src="../scripts/messages.js"></script>
</head>
<body>
<div class="menu">
    <img class="newMessageButton" src="../images/message.png" onclick="showSendBox()">
    <div class="dropdown" style="float: left">
        <select class="dropbtn" name="language">
            <option value="en" ${language == 'en' ? 'selected' : ''} onclick="changeLanguage('en')">English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''} onclick="changeLanguage('ru')">Русский</option>
        </select>
    </div>
    <a href="/controller?command=home"><img id="home" class="homeButton" src="../images/return.png"></a>
    <div class="dropdown">
        <button id="userBtn" class="dropbtn">${nickname}</button>
        <div class="dropdown-content">
            <a href="/controller?command=showMessages&page=1"><fmt:message key="menu.label.messages"/></a>
            <admin:access>
                <a href="/controller?command=showUsers&page=1"><fmt:message key="menu.label.users"/></a>
            </admin:access>
            <a href="/controller?command=showRating&page=1"><fmt:message key="menu.label.rating"/></a>
            <a href="/controller" onclick="logOut()"><fmt:message key="menu.label.logOut"/></a>
        </div>
    </div>
</div>

<div class="infoForm">
    <div>
        <h1><fmt:message key="table.infoForm.cash"/></h1>
    </div>
    <input id="cashField" type="text" disabled value="${money}">
    <div>
        <h1><fmt:message key="table.infoForm.bet"/></h1>
    </div>
    <input id="betField" type="text" disabled value="0">
    <div>
        <h1><A href="#" id="deposit" onclick="deposit()"><fmt:message key="table.infoForm.deposit"/></A></h1>
    </div>
</div>
<div id="enemyCardPane" class="cardPane">

</div>
<div id="myCardPane" class="cardPane">

</div>
<div class="commandPane">
    <div class="buttonPane">
        <div class="inner">
            <button id="deal" type="button" onclick="deal()"><fmt:message key="table.control.deal"/></button>
        </div>
        <div class="inner">
            <button id="hit" type="button" onclick="hit()"><fmt:message key="table.control.hit"/></button>
        </div>
        <div class="inner info" id="winLabel"><fmt:message key="table.info.win"/></div>
        <div class="inner info" id="loseLabel"><fmt:message key="table.info.lose"/></div>
        <div class="inner info" id="drawLabel"><fmt:message key="table.info.draw"/></div>
        <div class="inner info" id="winBlackJackLabel"><fmt:message key="table.info.winBlackJack"/></div>
        <div class="inner info" id="drawBlackJackLabel"><fmt:message key="table.info.drawBlackJack"/></div>
        <div class="inner info" id="loseBlackJackLabel"><fmt:message key="table.info.loseBlackJack"/></div>
        <div class="inner info" id="bustedLabel"><fmt:message key="table.info.busted"/></div>
        <div class="inner info" id="myPointsLabel"></div>
        <div class="inner">
            <button id="stand" type="button" onclick="stand()"><fmt:message key="table.control.stand"/></button>
        </div>
    </div>
    <div id="tokenForm" class="tokenForm">
        <div>
            <input id="token1" class="token" type="button" value="1" onclick="bet(1)">
        </div>
        <div>
            <input id="token5" class="token" type="button" value="5" onclick="bet(5)">
        </div>
        <div>
            <input id="token10" class="token" type="button" value="10" onclick="bet(10)">
        </div>
        <div>
            <input id="token50" class="token" type="button" value="50" onclick="bet(50)">
        </div>
        <div>
            <input id="token100" class="token" type="button" value="100" onclick="bet(100)">
        </div>
        <div>
            <input id="token500" class="token" type="button" value="500" onclick="bet(500)">
        </div>
    </div>


</div>
<div id="myModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <span onclick="closeModal()" class="close">&times;</span>
            <h2 id="info"><fmt:message key="modal.label.title"/></h2>
            <h2 id="success"><fmt:message key="modal.title.success"/></h2>
            <div>
                <label id="label" for="toBox"><fmt:message key="modal.label.user"/></label>
                <input id="toBox" type="text">
            </div>
        </div>
        <div class="modal-body">
            <div>
                <textarea id="receiveField" disabled></textarea>
                <div id="responseLabel"><fmt:message key="modal.label.response"/></div>
                <textarea id="responseField" placeholder='<fmt:message key="modal.holder.text"/>'><c:out
                        value="${errorMessage}"/></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <input id="sendButton" type="button" onclick="sendMessage()" value='<fmt:message key="modal.button.send"/>'>
        </div>
    </div>
</div>

</body>
</html>