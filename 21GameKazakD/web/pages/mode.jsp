<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="admin" uri="/WEB-INF/taglib.tld" %>
<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 06.02.2017
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en_EN'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="configuration/lang/text"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="${language}">
<head>
    <title>Режим игры</title>
    <link rel="stylesheet" href="../css/menu.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="../css/messages.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="../css/mode.css" media="screen" type="text/css"/>
    <link href="https://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic" rel="stylesheet">
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
<div id="mode-form">
    <h1><fmt:message key="mode.label.title"/></h1>
    <fieldset>
        <form name="modeForm" method="POST" action="controller">
            <input type="hidden" name="command" value="mode"/>
            <input type="submit" name="mode" value='<fmt:message key="mode.button.one"/>'>
            <input type="submit" name="mode" value='<fmt:message key="mode.button.two"/>'>
        </form>
    </fieldset>
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
                <textarea id="responseField" placeholder='<fmt:message key="modal.holder.text"/>'><c:out value="${errorMessage}"/></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <input id="sendButton" type="button" onclick="sendMessage()" value='<fmt:message key="modal.button.send"/>'>
        </div>
    </div>
</div>
</body>
</html>
