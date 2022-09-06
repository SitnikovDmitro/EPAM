<%@ page import="entity.Product, service.TextService, localization.Lang" %>
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



    <div class="modal fade" id="productAmountModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <form action="${pageContext.request.contextPath}/controller?action=addCashierProductToCheque" method="post" id="seniorCashierVerificationForm">
            <div class="modal-header">
              <h5 class="modal-title" id="modalLabel">${text.translate('Specify product count', lang)}</h5>
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
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showCashierProducts">${text.translate('Products', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showCashierCheques">${text.translate('Cheques', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showCashierCheque">${text.translate('Cheque', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showCashierOptions">${text.translate('Options', lang)}</a>
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
            <form action="${pageContext.request.contextPath}/controller?action=setCashierProducts" method="post">
              <input type="text" class="form-control mb-3" placeholder="${text.translate('Name', lang)}" name="name" id="nameInput" maxlength="100" value="${name}">
              <input type="number" class="form-control mb-3" placeholder="${text.translate('Code', lang)}" name="code" id="codeInput" min="0" max="1000000" step="1" value="${code}">
              <button type="submit" class="btn btn-primary">${text.translate('Search', lang)}</button>
            </form>
          </div>
        </div>



        <c:if test="${empty products}">
          <h3 class="text-center mb-3">${text.translate('No products found', lang)}</h3>
        </c:if>



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
                  <button class="btn btn-primary" onclick="openModal(${product.code}, ${product.countable});">${text.translate('Add this to cheque', lang)}</button>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>









        <div class="text-center">
          <div class="btn-group mt-3" role="group">
            <c:forEach var="page" items="${pages}">
              <a href="${pageContext.request.contextPath}/controller?action=showCashierProducts&page=${page}" type="button" class="btn btn-secondary">${page}</a>
            </c:forEach>
          </div>
        </div>




      </div>
    </div>

    <script>
      function openModal(code, countable) {
        if (countable) {
          modalLabel.textContent = "${text.translate('Specify product count', lang)}";
          amountInputLabel.textContent = "${text.translate('Count', lang)}";
          amountInput.step = "1";
        } else {
          modalLabel.textContent = "${text.translate('Specify product weight', lang)}";
          amountInputLabel.textContent = "${text.translate('Weight', lang)}";
          amountInput.step = "0.001";
        }
        productCodeInput.value = code;
        var myModal = new bootstrap.Modal(document.getElementById('productAmountModal'));
        myModal.show();
      }


    </script>




    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>