<%@ page import="entity.Order, java.util.ArrayList, entity.OrderState, localization.SiteText, localization.Lang" %>
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
        <a class="nav-link active" aria-current="page">${st.getText('orders', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showMasterEdit">${st.getText('edit_me', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${st.getText('logout', lg)}</a>
      </li>
    </ul>

    <div class="row m-3">
      <div class="container col-md-6">

        <c:choose>
          <c:when test="${not empty orders}">
            <c:forEach var="order" items="${orders}">

              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title">${order.title}</h5>
                  <p class="card-text">${order.description}</p>
                  <p class="card-text">${st.getStateText(order.state, lg)}</p>
                  <c:if test="${order.state == 'PAID'}">
                    <form action="${pageContext.request.contextPath}/controller?action=startOrder&id=${order.id}" method="post">
                      <button type="submit" class="btn btn-primary">${st.getText('start', lg)}</button>
                    </form>
                  </c:if>
                  <c:if test="${order.state == 'IN_WORK'}">
                    <form action="${pageContext.request.contextPath}/controller?action=finishOrder&id=${order.id}" method="post">
                      <button type="submit" class="btn btn-primary">${st.getText('finish', lg)}</button>
                    </form>
                  </c:if>
                </div>
              </div>

            </c:forEach>
          </c:when>
          <c:otherwise>

            <h3 class="text-center mb-3 mt-3">${st.getText('no_orders', lg)}</h3>

          </c:otherwise>
        </c:choose>


      </div>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>