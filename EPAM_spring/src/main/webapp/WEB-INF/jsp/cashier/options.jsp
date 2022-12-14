<%@ page import="com.ra.service.TextService, com.ra.enums.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/static/bootstrap.css" rel="stylesheet">
    <script src="/static/bootstrap.js"></script>
  </head>
  <body>
    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showProducts">${text.translate('Products', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showCheques">${text.translate('Cheques', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showCheque">${text.translate('Cheque', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showOptions">${text.translate('Options', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/logout">${text.translate('Log out', lang)}</a>
      </li>
    </ul>

    <h2 class="text-center mb-3 mt-3">${text.translate('Options', lang)}</h2>

    <div class="text-center">
      <div class="btn-group mt-3" role="group">
        <c:if test="${lang == 'UK'}">
          <a type="button" class="btn btn-outline-secondary">УКР</a>
          <a type="button" href="${pageContext.request.contextPath}/cashier/changeLanguage?lang=RU" class="btn btn-secondary">РУС</a>
          <a type="button" href="${pageContext.request.contextPath}/cashier/changeLanguage?lang=EN" class="btn btn-secondary">ENG</a>
        </c:if>

        <c:if test="${lang == 'RU'}">
          <a type="button" href="${pageContext.request.contextPath}/cashier/changeLanguage?lang=UK" class="btn btn-secondary">УКР</a>
          <a type="button" class="btn btn-outline-secondary">РУС</a>
          <a type="button" href="${pageContext.request.contextPath}/cashier/changeLanguage?lang=EN" class="btn btn-secondary">ENG</a>
        </c:if>

        <c:if test="${lang == 'EN'}">
          <a type="button" href="${pageContext.request.contextPath}/cashier/changeLanguage?lang=UK" class="btn btn-secondary">УКР</a>
          <a type="button" href="${pageContext.request.contextPath}/cashier/changeLanguage?lang=RU" class="btn btn-secondary">РУС</a>
          <a type="button" class="btn btn-outline-secondary">ENG</a>
        </c:if>
      </div>
    </div>
  </body>
</html>