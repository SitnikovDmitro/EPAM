<%@ page import="entity.Order, java.util.ArrayList, java.util.List, localization.SiteText, localization.Lang" %>
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
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showUserOrderCreate">${st.getText('create_order', lg)}</a>
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


    <h2 class="text-center mb-3 mt-3">${st.getText('feedback', lg)}</h2>

    <div class="row m-3">
      <div class="container col-md-6">

          <div class="card">
            <form action="${pageContext.request.contextPath}/controller?action=giveFeedback&id=${param.id}" method="post">
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  <div class="custom-control custom-radio">
                    <input type="radio" id="1" name="reviewStars" value="1" class="custom-control-input">
                    <label class="custom-control-label" for="1">${st.getText('1_stars', lg)}</label>
                  </div>
                  <div class="custom-control custom-radio">
                    <input type="radio" id="2" name="reviewStars" value="2" class="custom-control-input">
                    <label class="custom-control-label" for="2">${st.getText('2_stars', lg)}</label>
                  </div>
                  <div class="custom-control custom-radio">
                    <input type="radio" id="3" name="reviewStars" value="3" class="custom-control-input">
                    <label class="custom-control-label" for="3">${st.getText('3_stars', lg)}</label>
                  </div>
                  <div class="custom-control custom-radio">
                    <input type="radio" id="4" name="reviewStars" value="4" class="custom-control-input">
                    <label class="custom-control-label" for="4">${st.getText('4_stars', lg)}</label>
                  </div>
                  <div class="custom-control custom-radio">
                    <input type="radio" id="5" name="reviewStars" value="5" class="custom-control-input" checked>
                    <label class="custom-control-label" for="5">${st.getText('5_stars', lg)}</label>
                  </div>
                </li>

                <li class="list-group-item">
                  <textarea class="form-control" aria-label="With textarea" id="reviewText" name="reviewText" maxlength="400"></textarea>
                </li>

                <li class="list-group-item">
                  <button type="submit" class="btn btn-primary">${st.getText('submit', lg)}</button>
                  <a href="${pageContext.request.contextPath}/controller?action=showUserOrders" class="btn btn-secondary">${st.getText('cancel', lg)}</a>
                </li>
              </ul>
            </form>
          </div>



      


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>