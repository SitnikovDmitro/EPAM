<%@ page import="entity.Order, java.util.ArrayList, java.util.List, localization.SiteText, localization.Lang" %>
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

    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="modal_label" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <form action="${pageContext.request.contextPath}/controller?action=setOrdersFilter" method="post">
            <div class="modal-header">
              <h5 class="modal-title" id="modal_label">${st.getText('filtering_and_ordering', lg)}</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
              <p>${st.getText('order_criteria', lg)}:</p>
              <li class="list-group-item mb-3">
                <div class="custom-control custom-radio">
                  <input type="radio" id="date" name="orderCriteria" value="date" class="custom-control-input" checked>
                  <label class="custom-control-label" for="date">${st.getText('date', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="cost" name="orderCriteria" value="cost" class="custom-control-input">
                  <label class="custom-control-label" for="cost">${st.getText('cost', lg)}</label>
                </div>
              </li>

              <p>${st.getText('order_type', lg)}:</p>
              <li class="list-group-item mb-3">
                <div class="custom-control custom-radio">
                  <input type="radio" id="straight" name="orderType" value="straight" class="custom-control-input" checked>
                  <label class="custom-control-label" for="straight">${st.getText('straight', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="inverted" name="orderType" value="inverted" class="custom-control-input">
                  <label class="custom-control-label" for="inverted">${st.getText('inverted', lg)}</label>
                </div>
              </li>

              <p>${st.getText('filter_state', lg)}:</p>
              <li class="list-group-item mb-3">
                <div class="custom-control custom-radio">
                  <input type="radio" id="ALL" name="filterState" value="" class="custom-control-input" checked>
                  <label class="custom-control-label" for="all">${st.getText('all', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="WAIT_FOR_PAYMENT" name="filterState" value="WAIT_FOR_PAYMENT" class="custom-control-input">
                  <label class="custom-control-label" for="WAIT_FOR_PAYMENT">${st.getText('wait_for_payment', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="PAID" name="filterState" value="PAID" class="custom-control-input">
                  <label class="custom-control-label" for="PAID">${st.getText('paid', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="IN_WORK" name="filterState" value="IN_WORK" class="custom-control-input">
                  <label class="custom-control-label" for="IN_WORK">${st.getText('in_work', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="CANCELED" name="filterState" value="CANCELED" class="custom-control-input">
                  <label class="custom-control-label" for="CANCELED">${st.getText('canceled', lg)}</label>
                </div>
                <div class="custom-control custom-radio">
                  <input type="radio" id="COMPLETED" name="filterState" value="COMPLETED" class="custom-control-input">
                  <label class="custom-control-label" for="COMPLETED">${st.getText('completed', lg)}</label>
                </div>
              </li>

              <p>${st.getText('master', lg)}:</p>
              <input type="text" class="form-control" name="searchText" placeholder="Email" aria-label="Email" maxlength="20">
            </div>

            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${st.getText('cancel', lg)}</button>
              <button type="submit" class="btn btn-primary">${st.getText('apply', lg)}</button>
            </div>
          </form>
        </div>
      </div>
    </div>





    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link active" aria-current="page">${st.getText('orders', lg)}</a>
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

        <h2 class="text-center mb-3 mt-3">${st.getText('orders_searching', lg)}</h2>
        <button type="button" class="btn btn-outline-secondary text-center mb-3" data-bs-toggle="modal" data-bs-target="#exampleModal">${st.getText('filter_options', lg)}</button>

        <c:choose>
          <c:when test="${not empty orders}">
            <c:forEach var="order" items="${orders}">

              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title">${order.title}</h5>
                  <p class="card-text">${order.description}</p>
                </div>
                <ul class="list-group list-group-flush">
                  <c:if test="${order.cost >= 0}">
                    <li class="list-group-item">${order.getFormattedCost()}$</li>
                  </c:if>
                  <li class="list-group-item">${order.creationTime.toString()}</li>
                  <li class="list-group-item">${st.getStateText(order.state, lg)}</li>
                </ul>
                <div class="card-body">
                  <a href="${pageContext.request.contextPath}/controller?action=showManagerOrderEdit&id=${order.id}" class="btn btn-primary">${st.getText('edit', lg)}</a>
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