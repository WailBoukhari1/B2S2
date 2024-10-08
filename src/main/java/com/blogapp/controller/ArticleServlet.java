package com.blogapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogapp.model.Article;
import com.blogapp.model.ArticleStatus;
import com.blogapp.model.Author;
import com.blogapp.repository.ArticleRepository;
import com.blogapp.repository.AuthorRepository;
import com.blogapp.repository.impl.ArticleRepositoryImpl;
import com.blogapp.repository.impl.AuthorRepositoryImpl;
import com.blogapp.service.ArticleService;
import com.blogapp.service.AuthorService;

@WebServlet(name = "ArticleServlet", urlPatterns = {"/article/*"})
public class ArticleServlet extends HttpServlet {

    private ArticleService articleService;
    private AuthorService authorService;

    @Override
    public void init() throws ServletException {
        super.init();
        ArticleRepository articleRepository = new ArticleRepositoryImpl();
        AuthorRepository authorRepository = new AuthorRepositoryImpl();
        articleService = new ArticleService(articleRepository);
        authorService = new AuthorService(authorRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action == null ? "/list" : action) {
                case "/create":
                    showCreateForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteArticle(request, response);
                    break;
                case "/view":
                    viewArticle(request, response);
                    break;
                default:
                    listArticles(request, response);
            }
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "create":
                    createArticle(request, response);
                    break;
                case "edit":
                    updateArticle(request, response);
                    break;
                default:
                    listArticles(request, response);
            }
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }

    private void listArticles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = getIntParameter(request, "page", 1);
        int recordsPerPage = 10;
        String searchTitle = request.getParameter("searchTitle");

        List<Article> articles = articleService.getAllArticles((page - 1) * recordsPerPage, recordsPerPage, searchTitle);
        int noOfRecords = articleService.getNoOfRecords(searchTitle);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("articles", articles);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("searchTitle", searchTitle);

        forwardToJsp(request, response, "/WEB-INF/views/article/list.jsp");
    }

    private void viewArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getLongParameter(request, "id");
        Article article = articleService.getArticleById(id);
        request.setAttribute("article", article);
        forwardToJsp(request, response, "/WEB-INF/views/article/view.jsp");
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Author> authors = authorService.getAllAuthors();
        request.setAttribute("authors", authors);
        forwardToJsp(request, response, "/WEB-INF/views/article/create.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getLongParameter(request, "id");
        Article article = articleService.getArticleById(id);
        List<Author> authors = authorService.getAllAuthors();
        request.setAttribute("article", article);
        request.setAttribute("authors", authors);
        forwardToJsp(request, response, "/WEB-INF/views/article/edit.jsp");
    }

    private void createArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Long authorId = getLongParameter(request, "authorId");

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(authorService.getAuthorById(authorId));
        article.setCreationDate(LocalDateTime.now());
        article.setStatus(ArticleStatus.DRAFT);

        articleService.addArticle(article);
        response.sendRedirect(request.getContextPath() + "/article/list");
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getLongParameter(request, "id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Long authorId = getLongParameter(request, "authorId");

        Article article = articleService.getArticleById(id);
        if (article == null) {
            throw new IllegalArgumentException("Article not found");
        }
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(authorService.getAuthorById(authorId));

        articleService.updateArticle(article);
        response.sendRedirect(request.getContextPath() + "/article/view?id=" + id);
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getLongParameter(request, "id");
        articleService.deleteArticle(id);
        response.sendRedirect(request.getContextPath() + "/article/list");
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("error", "An error occurred: " + e.getMessage());
        forwardToJsp(request, response, "/WEB-INF/views/error.jsp");
    }

    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    private int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        String paramValue = request.getParameter(paramName);
        return paramValue == null ? defaultValue : Integer.parseInt(paramValue);
    }

    private Long getLongParameter(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        return paramValue == null ? null : Long.parseLong(paramValue);
    }
}
