<%@ page import="entity.Order, entity.OrderState, java.util.List, localization.SiteText, localization.Lang" %>
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

        <div class="card mb-3">
          <form action="${pageContext.request.contextPath}/controller?action=editOrder" method="post">
            <div class="card-body">
              <h5 class="card-title">${selectedOrder.title}</h5>
              <p class="card-text">${selectedOrder.description}</p>
            </div>


            <div class="card-body">
              <input type="number" class="form-control mb-3" min="0.00" max="100000.00" step="0.05" value="${selectedOrder.cost > 0 ? selectedOrder.getFormattedCost() : ''}" id="cost" name="cost"  placeholder="Order price" required>

              <select class="form-select mb-3" name="state">
                <option selected>${st.getStateText(selectedOrder.state, lg)}</option>
                <option value="WAIT_FOR_PAYMENT">${st.getText('wait_for_payment', lg)}</option>
                <option value="PAID">${st.getText('paid', lg)}</option>
                <option value="CANCELED">${st.getText('canceled', lg)}</option>
              </select>

              <c:if test="${empty selectedOrder.masterEmail}">
                <a href="${pageContext.request.contextPath}/controller?action=showMasters" class="btn btn-outline-secondary">${st.getText('appoint_master', lg)}</a>
              </c:if>

            </div>


            <div class="card-body">
              <button type="submit" class="btn btn-primary">${st.getText('apply', lg)}</button>
            </div>
          </form>
        </div>

      </div>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>