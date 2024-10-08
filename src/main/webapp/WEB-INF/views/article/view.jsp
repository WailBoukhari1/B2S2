<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${article.title}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">${article.title}</h1>
        <p><strong>Author:</strong> ${article.author.name}</p>
        <p><strong>Creation Date:</strong> ${article.creationDate}</p>
        <p><strong>Status:</strong> ${article.status}</p>
        <div class="mt-4">
            <h4>Content:</h4>
            <p>${article.content}</p>
        </div>
        <div class="mt-4">
            <a href="<c:url value='/article/edit?id=${article.id}'/>" class="btn btn-warning mr-2">Edit</a>
            <a href="<c:url value='/article/delete?id=${article.id}'/>" class="btn btn-danger mr-2" onclick="return confirm('Are you sure you want to delete this article?')">Delete</a>
            <a href="<c:url value='/article/list'/>" class="btn btn-secondary">Back to List</a>
        </div>
    </div>
</body>
</html>
