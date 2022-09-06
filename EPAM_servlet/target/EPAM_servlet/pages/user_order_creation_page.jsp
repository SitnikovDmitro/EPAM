<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="localization.SiteText, localization.Lang" %>
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
        <a class="nav-link active" aria-current="page">${st.getText('create_order', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showUserOrders">${st.getText('my_orders', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showUserEdit">${st.getText('edit_me', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${st.getText('logout', lg)}</a>
      </li>
    </ul>

    <div class="row m-3">
      <div class="container col-md-6">
        <h2 class="text-center mb-3 mt-3">${st.getText('order_creation', lg)}</h2>

        <form action="${pageContext.request.contextPath}/controller?action=createOrder" method="post">
          <div class="mb-3">
            <label for="title" class="form-label">${st.getText('title', lg)}</label>
            <input type="text" class="form-control" id="title" aria-describedby="emailHelp" maxlength="100" name="title" required>
          </div>

          <div class="mb-3">
            <label for="description" class="form-label" maxlength="400">${st.getText('description', lg)}</label>
            <textarea class="form-control" aria-label="With textarea" id="description" name="description" required></textarea>
          </div>

          <button type="submit" class="btn btn-primary">${st.getText('create_order', lg)}</button>
        </form>
      </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>