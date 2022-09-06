<%@ page import="entity.Cheque, service.TextService, localization.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>
  <body>



    <div class="modal fade" id="seniorCashierVerificationModal1" tabindex="-1" aria-labelledby="modalLabel1" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalLabel1">${text.translate('Senior cashier verification', lang)}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <div class="mb-3">
              <label for="usernameInput1" class="form-label">${text.translate('Username', lang)}</label>
              <input type="text" class="form-control" id="usernameInput1" maxlength="20" placeholder="${text.translate('Username', lang)}" required>
            </div>
            <div>
              <label for="passwordInput1" class="form-label">${text.translate('Password', lang)}</label>
              <input type="password" class="form-control" id="passwordInput1" maxlength="20" placeholder="${text.translate('Password', lang)}" required>
            </div>
            <input type="hidden" id="productCodeInput">
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${text.translate('Cancel', lang)}</button>
            <button type="button" class="btn btn-primary" onclick="completeModal1();">${text.translate('Submit', lang)}</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="seniorCashierVerificationModal2" tabindex="-1" aria-labelledby="modalLabel2" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalLabel2">${text.translate('Senior cashier verification', lang)}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <div class="mb-3">
              <label for="usernameInput2" class="form-label">${text.translate('Username', lang)}</label>
              <input type="text" class="form-control" id="usernameInput2" maxlength="20" placeholder="${text.translate('Username', lang)}" required>
            </div>
            <div>
              <label for="passwordInput2" class="form-label">${text.translate('Password', lang)}</label>
              <input type="password" class="form-control" id="passwordInput2" maxlength="20" placeholder="${text.translate('Password', lang)}" required>
            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${text.translate('Cancel', lang)}</button>
            <button type="button" class="btn btn-primary" onclick="completeModal2();">${text.translate('Submit', lang)}</button>
          </div>
        </div>
      </div>
    </div>



    <div class="modal fade" id="finishChequeModal" tabindex="-1" aria-labelledby="modalLabel3" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalLabel3">${text.translate('Finish cheque', lang)}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <div class="mb-3">
              <label for="cashInput" class="form-label">${text.translate('Cash', lang)}</label>
              <input type="text" class="form-control" id="cashInput" maxlength="20" oninput="changeModal3()">
            </div>
            <div class="mb-3">
              <label for="priceInput" class="form-label">${text.translate('Price', lang)}</label>
              <input type="text" class="form-control" id="priceInput" value="${text.formatPrice(price)}" disabled readonly>
            </div>
            <div>
              <label for="changeInput" class="form-label">${text.translate('Change', lang)}</label>
              <input type="text" class="form-control" id="changeInput" disabled readonly>
            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${text.translate('Cancel', lang)}</button>
            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=finishCashierCheque">${text.translate('Finish', lang)}</a>
          </div>
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
        <h2 class="text-center mb-3 mt-3">${text.translate('Cheque', lang)}</h2>



        <div class="card mb-3">


          <c:choose>
            <c:when test="${not empty chequeLines}">
              <ul class="list-group list-group-flush">
                <c:forEach var="chequeLine" items="${chequeLines}">

                  <li class="list-group-item">
                    <div class="row mt-2">
                      <div class="col-md-10">
                        <h5 class="card-title">${text.formatChequeLine(chequeLine, lang)}</h5>
                      </div>
                      <div class="col-md-2">
                        <div class="d-grid">
                          <button class="btn btn-outline-secondary mb-2" type="button" onclick="openModalForDeleteProduct(${chequeLine.product.code})">${text.translate('Remove', lang)}</button>
                        </div>
                      </div>
                    </div>
                  </li>

                </c:forEach>
              </ul>

              <div class="card-body">
                <a type="button" class="btn btn-primary" onclick="openModalForDeleteCheque()">${text.translate('Cancel', lang)}</a>
                <a type="button" class="btn btn-primary" onclick="openModalForFinishCheque()">${text.translate('Finish', lang)}</a>
                <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=showCashierProducts">${text.translate('Add next product', lang)}</a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="card-body">
                <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=showCashierProducts">${text.translate('Add first product', lang)}</a>
              </div>
            </c:otherwise>
          </c:choose>
        </div>

      </div>
    </div>


    <script>
      function completeModal1() {
        var data = new URLSearchParams();
        data.append("username", usernameInput1.value);
        data.append("password", passwordInput1.value);
        data.append("productCode", productCodeInput.value);

        fetch("${pageContext.request.contextPath}/controller?action=removeCashierProductFromCheque", { method: "POST", body: data }).then(
          response => response.json()
        ).then(
          data => {
            var result = data.result;
            if (result) {
              window.location.href = "${pageContext.request.contextPath}/controller?action=showCashierCheque";
            } else {
              usernameInput1.className = "form-control is-invalid";
              passwordInput1.className = "form-control is-invalid";
            }
          }
        ).catch(
          error => {
            console.log(error);
          }
        );
      }

      function completeModal2() {
        var data = new URLSearchParams();
        data.append("username", usernameInput2.value);
        data.append("password", passwordInput2.value);

        fetch("${pageContext.request.contextPath}/controller?action=removeCashierCheque", { method: "POST", body: data }).then(
          response => response.json()
        ).then(
          data => {
            var result = data.result;
            if (result) {
              window.location.href = "${pageContext.request.contextPath}/controller?action=showCashierCheque";
            } else {
              usernameInput2.className = "form-control is-invalid";
              passwordInput2.className = "form-control is-invalid";
            }
          }
        ).catch(
          error => {
            console.log(error);
          }
        );
      }

      function changeModal3() {
        var cash = parseFloat(cashInput.value);
        var price = parseFloat(priceInput.value);
        var change = cash - price;

        if (isNaN(cash) || cash < price) {
          changeInput.className = "form-control is-invalid";
          changeInput.value = "???";
        } else {
          changeInput.className = "form-control";
          changeInput.value = change;
        }
      }


      function openModalForDeleteProduct(code) {
        usernameInput1.className = "form-control";
        passwordInput1.className = "form-control";
        usernameInput1.value = "";
        passwordInput1.value = "";
        productCodeInput.value = code;
        var myModal = new bootstrap.Modal(document.getElementById('seniorCashierVerificationModal1'));
        myModal.show();
      }
      function openModalForDeleteCheque() {
        usernameInput2.className = "form-control";
        passwordInput2.className = "form-control";
        usernameInput2.value = "";
        passwordInput2.value = "";
        var myModal = new bootstrap.Modal(document.getElementById('seniorCashierVerificationModal2'));
        myModal.show();
      }

      function openModalForFinishCheque() {
        changeInput.className = "form-control";
        changeInput.value = "";
        var myModal = new bootstrap.Modal(document.getElementById('finishChequeModal'));
        myModal.show();
      }
    </script>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>