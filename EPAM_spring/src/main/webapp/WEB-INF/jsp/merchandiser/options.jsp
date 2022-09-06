<%@ page import="com.ra.model.service.TextService, com.ra.model.enums.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Hello, world!</title>
  </head>
  <body>
    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/merchandiser/showProducts">${text.translate('Products', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/merchandiser/showProduct">${text.translate('Product', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/merchandiser/showOptions">${text.translate('Options', lang)}</a>
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
          <a type="button" href="${pageContext.request.contextPath}/merchandiser/changeLanguage?lang=RU" class="btn btn-secondary">РУС</a>
          <a type="button" href="${pageContext.request.contextPath}/merchandiser/changeLanguage?lang=EN" class="btn btn-secondary">ENG</a>
        </c:if>

        <c:if test="${lang == 'RU'}">
          <a type="button" href="${pageContext.request.contextPath}/merchandiser/changeLanguage&lang=UK" class="btn btn-secondary">УКР</a>
          <a type="button" class="btn btn-outline-secondary">РУС</a>
          <a type="button" href="${pageContext.request.contextPath}/merchandiser/changeLanguage&lang=EN" class="btn btn-secondary">ENG</a>
        </c:if>

        <c:if test="${lang == 'EN'}">
          <a type="button" href="${pageContext.request.contextPath}/merchandiser/changeLanguage&lang=UK" class="btn btn-secondary">УКР</a>
          <a type="button" href="${pageContext.request.contextPath}/merchandiser/changeLanguage&lang=RU" class="btn btn-secondary">РУС</a>
          <a type="button" class="btn btn-outline-secondary">ENG</a>
        </c:if>
      </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>