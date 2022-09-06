<%@ page import="entity.User, java.util.ArrayList, java.util.List, localization.SiteText, localization.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showManagerOrders">${st.getText('orders', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page">${st.getText('users', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${st.getText('logout', lg)}</a>
      </li>
    </ul>


    <div class="row m-3">
      <div class="container col-md-6">

        <h2 class="text-center mb-3 mt-3">${st.getText('user_selection', lg)}</h2>

        <form action="${pageContext.request.contextPath}/controller?action=setUsersFilter" method="post">
          <div class="input-group mb-3">
            <input type="text" class="form-control" placeholder="Name or e-mail" aria-label="Name or email" aria-describedby="searchString" name="searchString" value="${searchText}" maxlength="20">
            <button class="btn btn-outline-secondary" type="submit" id="searchString">${st.getText('search', lg)}</button>
          </div>
        </form>


        <c:choose>
          <c:when test="${not empty users}">
            <c:forEach var="user" items="${users}">

              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title mb-3">${user.firstname} ${user.surname}</h5>
                  <p class="card-text">${user.phone}</p>
                  <p class="card-text">${user.email}</p>
                  <p class="card-text">${st.getFormattedBalance(user)}$</p>

                  <form action="${pageContext.request.contextPath}/controller?action=changeUserBalance&email=${user.email}" method="post">
                    <div class="input-group">
                      <input type="number" min="0.00" max="10000.00" step="0.05" value="0.00" id="balance" name="balance" class="form-control" placeholder="Price" aria-describedby="apply" required>
                      <button class="btn btn-outline-secondary" type="submit" id="apply">${st.getText('apply', lg)}</button>
                    </div>
                  </form>
                </div>
              </div>

            </c:forEach>
          </c:when>
          <c:otherwise>

            <h3 class="text-center mb-3 mt-3">${st.getText('no_user', lg)}</h3>

          </c:otherwise>
        </c:choose>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>