<%@ page import="app.entity.Product, app.service.TextService, app.localization.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="static/bootstrap.css" rel="stylesheet">
    <script src="static/bootstrap.js"></script>
  </head>
  <body>


    <div class="modal fade" id="productAmountModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <form action="${pageContext.request.contextPath}/controller?action=deliverMerchandiserProduct" method="post" id="seniorCashierVerificationForm">
            <div class="modal-header">
              <h5 class="modal-title" id="modalLabel">${text.translate('Specify count of delivery', lang)}</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
              <div class="mb-3">
                <label for="amountInput" class="form-label" id="amountInputLabel">${text.translate('Count', lang)}</label>
                <input type="number" class="form-control" name="amount" id="amountInput" min="0" max="1000000" step="1" required>
              </div>
              <input type="hidden" name="productCode" id="productCodeInput">
            </div>

            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${text.translate('Cancel', lang)}</button>
              <button type="submit" class="btn btn-primary">${text.translate('Submit', lang)}</button>
            </div>
          </form>
        </div>
      </div>
    </div>


    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showMerchandiserProducts">${text.translate('Products', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showMerchandiserProduct">${text.translate('Product', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showMerchandiserOptions">${text.translate('Options', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${text.translate('Log out', lang)}</a>
      </li>
    </ul>



    <div class="row m-3">
      <div class="container col-md-6">
        <h2 class="text-center mb-3 mt-3">${text.translate('Products', lang)}</h2>



        <div class="card mb-3">
          <div class="card-body">
            <form action="${pageContext.request.contextPath}/controller?action=setMerchandiserProducts" method="post">
              <input type="text" class="form-control mb-3" placeholder="${text.translate('Name', lang)}" name="name" id="nameInput" maxlength="100" value="${name}">
              <input type="number" class="form-control mb-3" placeholder="${text.translate('Code', lang)}" name="code" id="codeInput" min="0" max="1000000" step="1" value="${code}">
              <button type="submit" class="btn btn-primary">${text.translate('Search', lang)}</button>
            </form>
          </div>
        </div>







        <div class="row row-cols-1 row-cols-md-2 g-4">
          <c:forEach var="product" items="${products}">
            <div class="col">
              <div class="card h-100">
                <img class="card-img-top" src="files/images/${product.code}.jpg" onerror="this.onerror=null; this.src='files/images/default.jpg';" alt="Product image" height="200">
                <div class="card-body">
                  <h5 class="card-title">${product.title}</h5>
                </div>
                <ul class="list-group list-group-flush">
                  <c:if test="${product.countable}">
                    <li class="list-group-item">${text.translate('Total count', lang)} - ${product.totalAmount} ${text.translate('pieces', lang)}</li>
                    <li class="list-group-item">${text.translate('Cost', lang)} - ${text.formatPrice(product.price)} ${text.translate('dollars per piece', lang)}</li>
                  </c:if>
                  <c:if test="${!product.countable}">
                    <li class="list-group-item">${text.translate('Total weight', lang)} - ${text.formatWeight(product.totalAmount)} ${text.translate('kilograms', lang)}</li>
                    <li class="list-group-item">${text.translate('Cost', lang)} - ${text.formatPrice(product.price)} ${text.translate('dollars per kilogram', lang)}</li>
                  </c:if>
                  <li class="list-group-item">${text.translate('Code', lang)} - ${product.code}</li>
                </ul>
                <div class="card-body">
                  <form action="${pageContext.request.contextPath}/controller?action=deleteMerchandiserProduct" method="post">
                    <input type="hidden" name="productCode" value="${product.code}">
                    <button type="submit" class="btn btn-primary">${text.translate('Delete', lang)}</button>
                    <button type="button" class="btn btn-primary" onclick="openModal(${product.code}, ${product.countable})">${text.translate('Delivery', lang)}</button>
                  </form>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>


        <div class="text-center">
          <div class="btn-group mt-3" role="group">
            <c:forEach var="page" items="${pages}">
              <a href="${pageContext.request.contextPath}/controller?action=showMerchandiserProducts&page=${page}" type="button" class="btn btn-secondary">${page}</a>
            </c:forEach>
          </div>
        </div>

      </div>
    </div>


    <script>
      function openModal(code, countable) {
        if (countable) {
          modalLabel.textContent = "${text.translate('Specify count of delivery', lang)}";
          amountInputLabel.textContent = "${text.translate('Count', lang)}";
          amountInput.value = "";
          amountInput.step = "1";
        } else {
          modalLabel.textContent = "${text.translate('Specify weight of delivery', lang)}";
          amountInputLabel.textContent = "${text.translate('Weight', lang)}";
          amountInput.value = "";
          amountInput.step = "0.001";
        }
        productCodeInput.value = code;
        var myModal = new bootstrap.Modal(document.getElementById('productAmountModal'));
        myModal.show();
      }
    </script>
  </body>
</html>