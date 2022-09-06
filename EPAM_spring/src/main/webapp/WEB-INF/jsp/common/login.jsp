<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <title>Hello, world2!</title>
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
      </div>
    </div>

    <script>
      function loginFunction() {
        var data = new URLSearchParams();
        data.append("username", usernameInput.value);
        data.append("password", passwordInput.value);

        fetch("${pageContext.request.contextPath}/login", { method: "POST", body: data }).then(
          response => response.json()
        ).then(
          data => {
            var result = data.result;
            if (result == 1) {
              window.location.href = "${pageContext.request.contextPath}/cashier/showProducts";
            } else if (result == 3) {
              window.location.href = "${pageContext.request.contextPath}/merchandiser/showProducts";
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


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>