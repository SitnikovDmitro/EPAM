<%@ page import="entity.Order, entity.OrderState, entity.*, localization.SiteText, localization.Lang" %>
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
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showUserOrderCreate">${st.getText('create_order', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page">${st.getText('my_orders', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showUserEdit">${st.getText('edit_me', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${st.getText('logout', lg)}</a>
      </li>
    </ul>

    <div class="row m-3">
      <div class="container col-md-6">

        <h2 class="text-center mb-3 mt-3">${st.getText('orders_manipulation', lg)}</h2>

        <c:choose>
          <c:when test="${not empty orders}">
            <c:forEach var="order" items="${orders}">

              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title">${order.title}</h5>
                  <p class="card-text">${order.description}</p>
                </div>

                <ul class="list-group list-group-flush">
                  <li class="list-group-item">${st.getStateText(order.state, lg)}</li>
                  <li class="list-group-item">${order.cost < 0 ? st.getText('price_not_specified', lg) : order.getFormattedCost() += '$' }</li>
                  <li class="list-group-item">${order.creationTime.toString()}</li>
                </ul>

                <c:if test="${order.state == 'WAIT_FOR_PAYMENT'}">
                  <div class="card-body">

                    <c:if test="${order.cost >= 0 && loggedUser.balance >= order.cost}">
                      <a href="${pageContext.request.contextPath}/controller?action=payUserOrder&id=${order.id}" class="btn btn-primary">${st.getText('pay', lg)}</a>
                    </c:if>
                    <c:if test="${order.cost >= 0 && loggedUser.balance < order.cost}">
                      <button class="btn btn-danger">${st.getText('pay', lg)}</button>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/controller?action=cancelUserOrder&id=${order.id}" class="btn btn-primary">${st.getText('cancel', lg)}</a>

                  </div>
                </c:if>

                <c:if test="${order.reviewStars <= 0 && order.state == 'COMPLETED'}">
                  <div class="card-body">
                    <a href="${pageContext.request.contextPath}/controller?action=showReviewCreate&id=${order.id}" class="btn btn-primary">${st.getText('feedback', lg)}</a>
                  </div>
                </c:if>
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