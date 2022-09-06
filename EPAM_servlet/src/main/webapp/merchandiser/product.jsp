<%@ page import="service.TextService, localization.Lang" %>
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
        <h2 class="text-center mb-3 mt-3">${text.translate('Product creation', lang)}</h2>



        <div class="card mb-3">
          <div class="card-body">
            <form action="${pageContext.request.contextPath}/controller?action=createMerchandiserProduct" method="post" enctype="multipart/form-data">
              <div class="mb-3">
                <label for="title" class="form-label">${text.translate('Title', lang)}</label>
                <input type="text" class="form-control" id="title" name="title" maxlength="100" required>
              </div>

              <div class="mb-3">
                <label for="image" class="form-label">${text.translate('Image file', lang)}</label>
                <input type="file" class="form-control" id="image" name="image" accept="image/jpg">
              </div>

              <div class="mb-3">
                <label for="price" class="form-label">${text.translate('Price', lang)}</label>
                <div class="input-group mb-3">
                  <span class="input-group-text" id="priceSpan">${text.translate('dollars per item', lang)}</span>
                  <input type="number" class="form-control" id="price" name="price" min="0" max="100000" step="0.01" required>
                </div>
              </div>

              <div class="mb-3">
                <label for="amount" class="form-label">${text.translate('Total amount', lang)}</label>
                <input type="number" class="form-control" id="amount" name="amount" min="0" max="1000000" step="1" required>
              </div>

              <div class="form-check mb-3">
                <input class="form-check-input" type="checkbox" name="countable" value="enabled" id="countable" checked>
                <label class="form-check-label" for="countable">${text.translate('Countable', lang)}</label>
              </div>

              <button type="submit" class="btn btn-primary">${text.translate('Create product', lang)}</button>
            </form>
          </div>
        </div>


      </div>
    </div>

    <script>
      countable.onchange = function() {
        if (countable.checked) {
          priceSpan.textContent="${text.translate('dollars per item', lang)}";
          amount.step='1';
        } else {
          priceSpan.textContent="${text.translate('dollars per kilogram', lang)}";
          amount.step='0.01';
        }
      };
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>