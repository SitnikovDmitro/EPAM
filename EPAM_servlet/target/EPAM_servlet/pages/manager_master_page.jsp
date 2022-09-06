<%@ page import="entity.Master, entity.Order, java.util.ArrayList, localization.SiteText, localization.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


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

        <h2 class="text-center mb-3 mt-3">${st.getText('master_inspection', lg)}</h2>
        <h3 class="text-center mb-3 mt-3">${st.getText('master', lg)}</h3>


        <div class="card mb-3">
          <div class="card-body">
            <h5 class="card-title mb-3">${master.firstname} ${master.surname}</h5>
            <p class="card-text">${master.phone}</p>
            <p class="card-text">${master.email}</p>
          </div>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">${master.information}</li>
          </ul>

          <c:if test="${master.completedOrdersCount > 0}">
            <div class="card-body">
              <p class="card-text">${fn:length(orders)} ${st.getText('reviews', lg)}</p>

              <p class="card-text">${master.getFormattedAverageScore()} ${st.getText('average_score', lg)}</p>
              <div class="progress">
                <div class="progress-bar progress-bar-striped bg-warning" role="progressbar" style="width: ${master.getFormattedAverageScoreInPercents()}%" aria-valuemin="0" aria-valuemax="100"></div>
              </div>

            </div>
          </c:if>
        </div>

        <c:choose>
          <c:when test="${not empty orders}">
            <h3 class="text-center mb-3 mt-3">${st.getText('reviews', lg)}</h3>
            <c:forEach var="order" items="${orders}">

              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title mb-3">${order.userEmail}</h5>
                  <p class="card-text">${order.reviewText}</p>
                  <p class="card-text">${order.reviewStars} ${st.getText('stars', lg)}</p>
                  <div class="progress">
                    <div class="progress-bar progress-bar-striped bg-warning" role="progressbar" style="width: ${order.reviewStars*20}%"  aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                </div>
              </div>

            </c:forEach>
          </c:when>
          <c:otherwise>

            <h3 class="text-center mb-3 mt-3">${st.getText('no_reviews', lg)}</h3>

          </c:otherwise>
        </c:choose>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>