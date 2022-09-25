<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="static/bootstrap.css" rel="stylesheet">
    <script src="static/bootstrap.js"></script>
    <title>Error</title>
  </head>
  <body>









    <div class="row">
      <div class="container col-md-3">
        <div class="card mt-3">
          <div class="card-body">
            <h4 class="card-title text-center">${code}</h4>
          </div>

          <ul class="list-group list-group-flush">
            <li class="list-group-item">
              <h5 class="text-center">${description}</h5>
            </li>
          </ul>

          <div class="card-body">
            <a type="button" href="${pageContext.request.contextPath}" class="btn btn-primary">Login</a>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>