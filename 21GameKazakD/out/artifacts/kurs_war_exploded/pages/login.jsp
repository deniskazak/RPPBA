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
    <link rel="stylesheet" href="../css/login.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="../css/messages.css" media="screen" type="text/css"/>
    <script type="text/javascript" src="../scripts/menu.js"></script>
    <link rel="stylesheet" href="../css/menu.css" media="screen" type="text/css"/>
    <link href="https://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic" rel="stylesheet">
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
    <h1>Игра 21</h1>
    <fieldset>
        <form name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="login"/>
            <input type="email" name="email" required value="<fmt:message key="login.label.email"/>"
                   onBlur="if(this.value=='')this.value='<fmt:message key="login.label.email"/>'"
                   onFocus="if(this.value=='<fmt:message key="login.label.email"/>')this.value='' ">
            <input type="password" name="password" required value="<fmt:message key="login.label.password"/>"
                   onBlur="if(this.value=='')this.value='<fmt:message key="login.label.password"/>'"
                   onFocus="if(this.value=='<fmt:message key="login.label.password"/>')this.value='' ">
            <input type="submit" value="<fmt:message key="login.button.submit"/>">
            <footer class="clearfix">
                <p><span class="info"></span>
                <form name="submitForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="registration">
                    <A HREF="/controller?command=registration"><fmt:message key="login.label.signUp"/></A>
                </form>
                </p>
                <p><c:out value="${errorMessage}"/></p>
            </footer>
        </form>
    </fieldset>
</div>

</body>
</html>
