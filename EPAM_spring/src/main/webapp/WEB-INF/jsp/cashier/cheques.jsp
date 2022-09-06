<%@ page import="com.ra.model.entity.Cheque, com.ra.model.service.TextService, com.ra.model.enums.Lang" %>
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

    <div class="modal fade" id="seniorCashierVerificationModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalLabel">${text.translate('Senior cashier verification', lang)}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <div class="mb-3">
              <label for="username" class="form-label">${text.translate('Username', lang)}</label>
              <input type="text" class="form-control" id="usernameInput" maxlength="20" placeholder="${text.translate('Username', lang)}" required>
            </div>
            <div>
              <label for="password" class="form-label">${text.translate('Password', lang)}</label>
              <input type="password" class="form-control" id="passwordInput" maxlength="20" placeholder="${text.translate('Password', lang)}" required>
            </div>
            <input type="hidden" id="reportTypeInput">
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${text.translate('Cancel', lang)}</button>
            <button type="button" class="btn btn-primary" onclick="completeModal();">${text.translate('Submit', lang)}</button>
          </div>
        </div>
      </div>
    </div>

    <ul class="nav justify-content-center">
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showProducts">${text.translate('Products', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showCheques">${text.translate('Cheques', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showCheque">${text.translate('Cheque', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/cashier/showOptions">${text.translate('Options', lang)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/logout">${text.translate('Log out', lang)}</a>
      </li>
    </ul>




    <div class="row">
      <div class="container col-md-6">
        <h2 class="text-center mb-3 mt-3">${text.translate('Cheques', lang)}</h2>



        <div class="card mb-3">
          <div class="card-body">
            <form action="${pageContext.request.contextPath}/cashier/setCheques" method="post">
              <div class="row">
                <div class="col-sm-6">
                  <div class="mb-3">
                    <label for="fromPriceInput" class="form-label">${text.translate('From price', lang)}</label>
                    <input type="number" class="form-control" id="fromPriceInput" name="fromPrice" min="0.00" max="100000.00" step="0.01" value="${fromPrice}">
                  </div>
                  <div class="form-check mb-3">
                    <input class="form-check-input" type="radio" name="sortCriteria" id="radio1" value="price" ${sortCriteria == "price" ? "checked" : ""}>
                    <label class="form-check-label" for="radio1">${text.translate('Sort by price', lang)}</label>
                  </div>
                </div>
                <div class="col-sm-6">
                  <div class="mb-3">
                    <label for="toPriceInput" class="form-label">${text.translate('To price', lang)}</label>
                    <input type="number" class="form-control" id="toPriceInput" name="toPrice" min="0.00" max="100000.00" step="0.01" value="${toPrice}">
                  </div>
                  <div class="form-check mb-3">
                    <input class="form-check-input" type="radio" name="sortCriteria" id="radio2" value="date" ${sortCriteria != "price" ? "checked" : ""}>
                    <label class="form-check-label" for="radio2">${text.translate('Sort by date', lang)}</label>
                  </div>
                </div>
              </div>
              <button type="submit" class="btn btn-primary">${text.translate('Search', lang)}</button>
            </form>
          </div>
        </div>

        <div class="card mb-3">
          <div class="card-body">
             <button type="button" class="btn btn-primary" onclick="openModalForCreateReport('X');">${text.translate('Create X-report', lang)}</button>
             <button type="button" class="btn btn-primary" onclick="openModalForCreateReport('Y');">${text.translate('Create Y-report', lang)}</button>
          </div>
        </div>

        <div class="row">
          <div class="container col-sm-6">

            <c:choose>
              <c:when test="${not empty cheques}">
                <c:forEach var="cheque" items="${pageCheques}" varStatus="loop">

                  <div class="card mb-3">
                    <ul class="list-group list-group-flush">
                      <c:forEach var="chequeLine" items="${pageChequeLines[loop.index]}">
                        <li class="list-group-item">
                          <h5 class="card-title">${text.formatChequeLine(chequeLine, lang)}</h5>
                        </li>
                      </c:forEach>

                      <li class="list-group-item">
                        <h5 class="card-title">${text.translate('Total', lang)} - ${text.formatPrice(cheque.price)} ${text.translate('dollars', lang)}</h5>
                      </li>
                      <li class="list-group-item">
                        <h5 class="card-title">${text.translate('Created', lang)} ${cheque.creationTime}</h5>
                      </li>
                    </ul>
                  </div>

                </c:forEach>
              </c:when>
              <c:otherwise>

                <h3 class="text-center mb-3 mt-3">${text.translate('No cheques', lang)}</h3>

              </c:otherwise>
            </c:choose>
          </div>






        <div class="text-center">
          <div class="btn-group mt-3" role="group">
            <c:forEach var="page" items="${pages}">
              <a href="${pageContext.request.contextPath}/cashier/showCheques?page=${page}" type="button" class="btn btn-secondary">${page}</a>
            </c:forEach>
          </div>
        </div>

      </div>
    </div>


    <script>
      function completeModal(action, success) {
        var data = new URLSearchParams();
        data.append("username", usernameInput.value);
        data.append("password", passwordInput.value);
        data.append("reportType", reportTypeInput.value);

        fetch("${pageContext.request.contextPath}/cashier/createReport", { method: "POST", body: data }).then(
          response => response.json()
        ).then(
          data => {
            var result = data.result;
            if (result >= 0) {
              var myModal = new bootstrap.Modal(document.getElementById('seniorCashierVerificationModal'));
              myModal.close();
              window.location.href = "${pageContext.request.contextPath}/cashier/getReport?number="+data.result;
            } else {
              usernameInput.className = "form-control is-invalid";
              passwordInput.className = "form-control is-invalid";
            }
          }
        ).catch(
          error => {
            console.log(error);
          }
        );
      }


      function openModalForCreateReport(reportType) {
        usernameInput.className = "form-control";
        passwordInput.className = "form-control";
        usernameInput.value = "";
        passwordInput.value = "";
        reportTypeInput.value = reportType;
        var myModal = new bootstrap.Modal(document.getElementById('seniorCashierVerificationModal'));
        myModal.show();
      }
    </script>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>