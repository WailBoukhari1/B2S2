<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Article</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Edit Article</h1>
        <form action="<c:url value='/article/edit'/>" method="post">
            <input type="hidden" name="id" value="${article.id}">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" class="form-control" id="title" name="title" value="${article.title}" required>
            </div>
            <div class="form-group">
                <label for="content">Content:</label>
                <textarea class="form-control" id="content" name="content" rows="5" required>${article.content}</textarea>
            </div>
            <div class="form-group">
                <label for="authorId">Author:</label>
                <select class="form-control" id="authorId" name="authorId" required>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}" ${article.author.id eq author.id ? 'selected' : ''}>${author.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Update Article</button>
            <a href="<c:url value='/article/list'/>" class="btn btn-secondary">Back to List</a>
        </form>
    </div>
</body>
</html>
