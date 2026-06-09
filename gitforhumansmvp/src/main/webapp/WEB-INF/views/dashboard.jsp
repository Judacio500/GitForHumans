<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Code Vault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body style="align-items: flex-start;">

    <div class="app-layout">
        
        <aside class="sidebar">
            <div class="brand">
                📦 Code Vault
            </div>
            
            <button type="button" class="btn-primary" onclick="openModal()">+ New Repository</button>

            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-item active">My Vault</a>
                <a href="#" class="nav-item">Shared with me</a>
                <a href="#" class="nav-item">Starred</a>
            </nav>

            <div class="user-profile">
                <p>${not empty sessionScope.loggedUser.name ? sessionScope.loggedUser.name : 'Developer'}</p>
                <a href="${pageContext.request.contextPath}/logout" style="color: var(--danger-text); font-size: 12px; text-decoration: none; display: block; margin-top: 5px;">Sign Out</a>
            </div>
        </aside>

        <main class="main-wrapper">
            
            <header class="topbar">
                <input type="text" class="search-bar" placeholder="Search in Vault...">
            </header>

            <div class="content-scroll">
                
                <h2 class="section-title">Recent Repositories</h2>
                
                <c:choose>
                    <c:when test="${fn:length(repositoryList) > 0}">
                        
                        <div class="repo-grid">
                            <c:forEach items="${repositoryList}" var="repo">
                                <a href="${pageContext.request.contextPath}/repository?id=${repo.idRepository}" class="repo-card">
                                    <h3>${repo.name}</h3>
                                    <p>${repo.description}</p>
                                </a>
                            </c:forEach>
                        </div>

                        <h2 class="section-title">All Repositories</h2>
                        <div class="list-view">
                            <div class="list-row header">
                                <div>Name</div>
                                <div>Owner</div>
                                <div>Created Date</div>
                                <div>Action</div>
                            </div>

                            <c:forEach items="${repositoryList}" var="repo">
                                <a href="${pageContext.request.contextPath}/repository?id=${repo.idRepository}" class="list-row">
                                    <div style="font-weight: 600; color: var(--accent);">${repo.name}</div>
                                    <div>Me</div>
                                    <div>${repo.creationDate.toLocalDate()}</div>
                                    <div style="color: var(--text-secondary);">Open &rarr;</div>
                                </a>
                            </c:forEach>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; padding: 50px; background-color: var(--surface); border-radius: var(--radius-card); border: 2px dashed #E1E4E8;">
                            <h3 style="color: var(--text-secondary); margin-bottom: 10px;">Your vault is empty</h3>
                            <p style="color: #9CA3AF; font-size: 14px;">Click the "+ New Repository" button on the sidebar to start tracking your projects.</p>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </main>
    </div>

    <div id="modalNewRepo" class="modal-overlay" style="display: none;">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Create New Repository</h3>
                <button type="button" class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            
            <form action="${pageContext.request.contextPath}/NewRepository" method="POST">
                <div class="form-group">
                    <label for="repositoryName">Repository Name</label>
                    <input type="text" id="repositoryName" name="repositoryName" required pattern="[a-zA-Z0-9_-]+" title="Only letters, numbers, dashes and underscores">
                </div>
                
                <div class="form-group">
                    <label for="description">Description (Optional)</label>
                    <textarea id="description" name="description" rows="3"></textarea>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="btn-secondary" onclick="closeModal()">Cancel</button>
                    <button type="submit" class="btn-primary">Create Repository</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openModal() {
            document.getElementById('modalNewRepo').style.display = 'flex';
        }
        function closeModal() {
            document.getElementById('modalNewRepo').style.display = 'none';
        }
    </script>

</body>
</html>