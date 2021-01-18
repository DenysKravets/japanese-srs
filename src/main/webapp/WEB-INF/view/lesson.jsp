<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="css/login.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Login</title>
</head>
<body style="color:aliceblue">

    <h1>Lesson</h1>

    <c:forEach items="${vocabs}" var="vocab">
        <div style="color:grey" class="card" style="width: 18rem;">
            <div class="card-header">
              ${vocab.word}
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">Reading: ${vocab.reading}</li>
              <li class="list-group-item">Meaning: ${vocab.meanings[0]}</li>
            </ul>
            <div class="vocab_id" value=${vocab.id} style="display: none;">${vocab.id}</div>
          </div>
    </c:forEach>

    <button id="next-lesson" type="button" class="btn btn-primary">Next Lesson</button>
    <button id="done" type="button" class="btn btn-warning">My work here is done</button>
        
    



    <jsp:include page="relativeURL"/>

    <script src="js/lesson.js"></script>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>
</html>