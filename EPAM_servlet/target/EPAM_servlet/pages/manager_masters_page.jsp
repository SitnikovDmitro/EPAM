<%@ page import="entity.Master, java.util.ArrayList, java.util.List, localization.SiteText, localization.Lang" %>
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
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showUsers">${st.getText('users', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${st.getText('logout', lg)}</a>
      </li>
    </ul>



    <div class="row m-3">
      <div class="container col-md-6">

        <h2 class="text-center mb-3 mt-3">${st.getText('master_selection', lg)}</h2>

        <c:choose>
          <c:when test="${not empty masters}">
            <c:forEach var="master" items="${masters}">


              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title mb-3">${master.firstname}</h5>
                  <p class="card-text">${master.phone}</p>
                  <p class="card-text">${master.email}</p>
                </div>

                <c:if test="${master.completedOrdersCount > 0}">
                  <div class="card-body">
                    <div class="progress">
                      <div class="progress-bar progress-bar-striped bg-warning" role="progressbar" style="width: ${master.getFormattedAverageScoreInPercents()}%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                  </div>
                </c:if>

                <div class="card-body">
                  <form action="${pageContext.request.contextPath}/controller?action=setMaster&email=${master.email}" method="post">
                    <button type="submit" class="btn btn-primary">${st.getText('select', lg)}</button>
                    <a href="${pageContext.request.contextPath}/controller?action=showManagerMaster&email=${master.email}" class="btn btn-secondary">${st.getText('show_details', lg)}</a>
                  </form>
                </div>
              </div>

            </c:forEach>
          </c:when>
          <c:otherwise>

            <h3 class="text-center mb-3 mt-3">${st.getText('no_masters', lg)}</h3>

          </c:otherwise>
        </c:choose>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>