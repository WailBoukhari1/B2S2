<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Article List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Article List</h1>
        <a href="<c:url value='/article/create'/>" class="btn btn-primary mb-3">Create New Article</a>
        
        <form action="<c:url value='/article/list'/>" method="get" class="mb-3">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search by title" name="searchTitle" value="${searchTitle}">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="submit">Search</button>
                </div>
            </div>
        </form>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Creation Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="article" items="${articles}">
                    <tr>
                        <td>${article.title}</td>
                        <td>${article.author.name}</td>
                        <td>${article.creationDate}</td>
                        <td>${article.status}</td>
                        <td>
                            <a href="<c:url value='/article/view?id=${article.id}'/>" class="btn btn-sm btn-info">View</a>
                            <a href="<c:url value='/article/edit?id=${article.id}'/>" class="btn btn-sm btn-warning">Edit</a>
                            <a href="<c:url value='/article/delete?id=${article.id}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this article?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination">
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <li class="page-item ${currentPage eq i ? 'active' : ''}">
                        <a class="page-link" href="<c:url value='/article/list?page=${i}&searchTitle=${searchTitle}'/>">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</body>
</html>
