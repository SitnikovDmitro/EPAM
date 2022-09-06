<%@ page import="entity.Master, java.util.ArrayList, java.util.List, localization.SiteText, localization.Lang " %>
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
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=showMasterOrders">${st.getText('orders', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page">${st.getText('edit_me', lg)}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=logout">${st.getText('logout', lg)}</a>
      </li>
    </ul>


    <div class="row m-3">
      <div class="container col-md-6">

        <h2 class="text-center mb-3 mt-3">${st.getText('master_editing', lg)}</h2>

        <form action="${pageContext.request.contextPath}/controller?action=editMaster" method="post">

          <div class="mb-3">
            <label for="email" class="form-label">${st.getText('email_address', lg)}</label>
            <input type="text" class="form-control" id="email" value="${loggedMaster.email}"disabled readonly>
          </div>

          <div class="progress mb-3">
            <div class="progress-bar progress-bar-striped bg-warning" role="progressbar" style="width: ${loggedMaster.getFormattedAverageScoreInPercents()}%"></div>
          </div>

          <div class="mb-3">
            <label for="password" class="form-label">${st.getText('password', lg)}</label>
            <input type="password" class="form-control" id="password" name="password" maxlength="20" minlength="8" value="${loggedMaster.password}" required>
          </div>

          <div class="mb-3">
            <label for="firstname" class="form-label">${st.getText('name', lg)}</label>
            <input type="text" class="form-control" id="firstname" name="firstname" maxlength="20" minlength="2" value="${loggedMaster.firstname}" required>
          </div>

          <div class="mb-3">
            <label for="surname" class="form-label">${st.getText('surname', lg)}</label>
            <input type="text" class="form-control" id="surname" name="surname" maxlength="20" minlength="2" value="${loggedMaster.surname}" required>
          </div>

          <div class="mb-3">
            <label for="phone" class="form-label">${st.getText('phone', lg)}</label>
            <input type="tel" class="form-control" id="phone" name="phone" maxlength="20" minlength="5" value="${loggedMaster.phone}" required>
          </div>

          <div class="mb-3">
            <label for="info" class="form-label" maxlength="400">${st.getText('information', lg)}</label>
            <textarea class="form-control" aria-label="With textarea" id="info" name="information">${loggedMaster.information}</textarea>
          </div>

          <button type="submit" class="btn btn-primary">${st.getText('save_changes', lg)}</button>
        </form>

      </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>