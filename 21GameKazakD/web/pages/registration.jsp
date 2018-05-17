<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 29.01.2017
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en_EN'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="configuration/lang/text"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="${language}">
<head>
    <link rel="stylesheet" href="../css/registration.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="../css/menu.css" media="screen" type="text/css"/>
    <link href="https://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic" rel="stylesheet">
    <script type="text/javascript" src="../scripts/menu.js"></script>
    <script type="text/javascript" src="../scripts/registration.js"></script>
    <title>Registration</title>
</head>
<body>
<div class="menu">
    <div class="dropdown" style="float: left">
        <select class="dropbtn" name="language">
            <option value="en" ${language == 'en' ? 'selected' : ''} onclick="changeLanguage('en')">English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''} onclick="changeLanguage('ru')">Русский</option>
        </select>
    </div>
</div>
<div id="login-form">
    <h1><fmt:message key="registration.label.title"/></h1>

    <fieldset >
        <form name="registrationForm" method="POST" action="controller" onsubmit="return validateForm()">
            <input type="hidden" name="command" value="signUp" />
            <div><fmt:message key="registration.label.email"/></div>
            <input type="email" class="emailField" name="email" maxlength="64" required>
            <fmt:message key="registration.label.nickName"/>
            <input type="text" name="nickName" minlength="5" required>
            <fmt:message key="registration.label.password"/>
            <input type="password" name="password" minlength="6" required>
            <fmt:message key="registration.label.password"/>
            <input type="password" name="repeat" required>
            <input type="submit" value='<fmt:message key="registration.button.submit"/>'>
            <footer class="clearfix">
                <p>
                    <span id="passwordMatchError" class="info"><fmt:message key="registration.matchError.password"/></span>
                    <span id="emailError" class="info"><fmt:message key="registration.error.email"/></span>
                    <span id="nicknameError" class="info"><fmt:message key="registration.error.nickname"/></span>
                    <span id="passwordError" class="info"><fmt:message key="registration.error.password"/></span>
                    <span id="serverError" class="info">${errorMessage}</span>
                </p>
            </footer>
        </form>
    </fieldset>
</div>
</body>
</html>
