<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
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
            <button type="button" href="${pageContext.request.contextPath}" class="btn btn-primary" data-bs-dismiss="modal">Login</button>
          </div>
        </div>
      </div>
    </div>




    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>