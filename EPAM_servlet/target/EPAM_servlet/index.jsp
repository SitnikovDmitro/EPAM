<%@ page import="app.service.TextService, app.localization.Lang" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/taglib.tld" prefix="m" %>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="static/bootstrap.css" rel="stylesheet">
    <script src="static/bootstrap.js"></script>
  </head>
  <body>

    
    <div class="row">
      <div class="container col-md-6">
        <h2 class="text-center mb-3 mt-3">Login</h2>
        <div class="card">
          <div class="card-body">
            <div class="mb-3">
              <label for="usernameInput" class="form-label">Username</label>
              <input type="text" class="form-control" id="usernameInput" maxlength="20" required>
              <div class="invalid-feedback" id="usernameInputFeedback"></div>
            </div>
            <div class="mb-3">
              <label for="passwordInput" class="form-label">Password</label>
              <input type="password" class="form-control" id="passwordInput" maxlength="20" required>
              <div class="invalid-feedback" id="passwordInputFeedback"></div>
            </div>
            <button type="button" class="btn btn-primary" id="submitButton">Submit</button>
          </div>
        </div>

        <h5 class="text-center mb-3 mt-3">
          <m:myName/>
        </h5>

      </div>
    </div>

    <script>
      function loginFunction() {
        var data = new URLSearchParams();
        data.append("username", usernameInput.value);
        data.append("password", passwordInput.value);

        fetch("${pageContext.request.contextPath}/controller?action=login", { method: "POST", body: data }).then(
          response => response.json()
        ).then(
          data => {
            var result = data.result;
            if (result == 1) {
              window.location.href = "${pageContext.request.contextPath}/controller?action=showCashierProducts";
            } else if (result == 3) {
              window.location.href = "${pageContext.request.contextPath}/controller?action=showMerchandiserProducts";
            } else {
              usernameInput.className = "form-control is-invalid";
              passwordInput.className = "form-control is-invalid";
              usernameInputFeedback.textContent="Invalid credentials";
              passwordInputFeedback.textContent="Invalid credentials";
            }
          }
        ).catch(
          error => {
            console.log(error);
          }
        );
      }

      submitButton.onclick = function() {
        loginFunction();
      };
    </script>
  </body>
</html>