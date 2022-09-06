

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, localization.SiteText, localization.Lang, localization.SiteText, localization.Lang" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Hello, world!</title>
  </head>
  <body>


    


    <div class="row  m-3">
      <div class="container col-md-6">
        <h2 class="text-center mb-3 mt-3">${st.getText('login', lg)}</h2>

        <form action="${pageContext.request.contextPath}/controller?action=login" method="post">
          <div class="mb-3">
            <label for="email_input" class="form-label">${st.getText('email_address', lg)}</label>

            <c:choose>
              <c:when test="${param.invalid}">
                <input type="email" class="form-control is-invalid" id="email_input" aria-describedby="emailHelp" name="email" maxlength="100" required>
                <div class="invalid-feedback">${st.getText('invalid_credentials', lg)}</div>
              </c:when>
              <c:otherwise>
                <input type="email" class="form-control" id="email_input" aria-describedby="emailHelp" name="email" maxlength="100" required>
              </c:otherwise>
            </c:choose>
          </div>

          <div class="mb-3">
            <label for="password_input" class="form-label">${st.getText('password', lg)}</label>

            <c:choose>
              <c:when test="${param.invalid}">
                <input type="password" class="form-control is-invalid" id="password_input" name="password" maxlength="20" required>
                <div class="invalid-feedback">${st.getText('invalid_credentials', lg)}</div>
              </c:when>
              <c:otherwise>
                <input type="password" class="form-control" id="password_input" name="password" maxlength="20" required>
              </c:otherwise>
            </c:choose>
          </div>

          <button type="submit" class="btn btn-primary">${st.getText('submit', lg)}</button>
          <a href="${pageContext.request.contextPath}" type="button" class="btn btn-secondary">${st.getText('cancel', lg)}</a>
        </form>
      </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>