<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Code Vault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body style="align-items: flex-start;"> <div class="app-layout">
        
        <aside class="sidebar">
            <div class="brand">
                Code Vault
            </div>
            
            <button type="button" class="btn-primary">+ New Repository</button>

            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-item active">My Vault</a>
                <a href="#" class="nav-item">Shared with me</a>
                <a href="#" class="nav-item">Starred</a>
            </nav>

            <div class="user-profile">
                <p>${not empty sessionScope.loggedUser.name ? sessionScope.loggedUser.name : 'Developer'}</p>
                <a href="${pageContext.request.contextPath}/home" style="color: var(--danger-text); font-size: 12px; text-decoration: none; display: block; margin-top: 5px;">Sign Out</a>
            </div>
        </aside>

        <main class="main-wrapper">
            
            <header class="topbar">
                <input type="text" class="search-bar" placeholder="Search in Vault...">
            </header>

            <div class="content-scroll">
                
                <h2 class="section-title">Recent Repositories</h2>
                <div class="repo-grid">
                    
                    <a href="${pageContext.request.contextPath}/repository" class="repo-card">
                        <h3>Project_Sésamo</h3>
                        <p>Automated vertical hydroponics system files and technical hardware specifications.</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/repository" class="repo-card">
                        <h3>Neuron.c</h3>
                        <p>Neural network developed from scratch in C with step by step MLP training.</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/repository" class="repo-card">
                        <h3>Git_Cat_JDGA</h3>
                        <p>HTML and web design base structures.</p>
                    </a>
                </div>

                <h2 class="section-title">All Repositories</h2>
                <div class="list-view">
                    <div class="list-row header">
                        <div>Name</div>
                        <div>Owner</div>
                        <div>Last Modified</div>
                        <div>Size</div>
                    </div>

                    <a href="${pageContext.request.contextPath}/repository" class="list-row">
                        <div style="font-weight: 600; color: var(--accent);">Project_Sésamo</div>
                        <div>Me</div>
                        <div>Jun 5, 2026</div>
                        <div>14.2 MB</div>
                    </a>

                    <a href="${pageContext.request.contextPath}/repository" class="list-row">
                        <div style="font-weight: 600; color: var(--accent);">Neuron.c</div>
                        <div>Me</div>
                        <div>May 28, 2026</div>
                        <div>2.1 MB</div>
                    </a>
                    
                    <a href="${pageContext.request.contextPath}/repository" class="list-row">
                        <div style="font-weight: 600; color: var(--accent);">ChiloDOGS_Finance</div>
                        <div>darkiman18</div>
                        <div>May 18, 2026</div>
                        <div>1.5 MB</div>
                    </a>
                </div>

            </div> </main>
    </div>

</body>
</html>