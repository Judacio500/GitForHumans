<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${repository.name} - Code Vault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body style="align-items: flex-start;">

    <div class="app-layout">
        
        <aside class="sidebar">
            <a href="${pageContext.request.contextPath}/dashboard" class="brand" style="text-decoration: none;">
                📦 Code Vault
            </a>
            
            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-item">&larr; Back to Dashboard</a>
            </nav>

            <div class="user-profile">
                <p>${not empty sessionScope.loggedUser.name ? sessionScope.loggedUser.name : 'Developer'}</p>
                <a href="${pageContext.request.contextPath}/logout" style="color: var(--danger-text); font-size: 12px; text-decoration: none; display: block; margin-top: 5px;">Sign Out</a>
            </div>
        </aside>

        <main class="main-wrapper">
            
            <header class="topbar">
                <input type="text" class="search-bar" placeholder="Search in ${repository.name}...">
            </header>

            <div class="content-scroll">
                
                <div class="repo-header">
                    <div class="repo-info">
                        <h2>${repository.name}</h2>
                        <p style="color: var(--text-secondary); font-size: 14px; margin-top: 5px;">${repository.description}</p>
                        
                        <div class="storage-widget">
                            <span>Storage Usage</span>
                            <div class="progress-bar">
                                <div class="progress-fill"></div>
                            </div>
                            <span>65%</span>
                        </div>
                    </div>
                    
                    <button class="btn-primary" style="width: auto;" onclick="openUploadModal()">+ Upload Asset</button>
                </div>

                <div class="lens-switcher">
                    <button class="tab-btn active">LFS Vault (Binaries)</button>
                    <button class="tab-btn">Source Code (Git)</button>
                </div>

                <div class="repo-container">
                    
                    <div class="view-lens active">
                        <c:choose>
                            <c:when test="${fn:length(repoMetaData) > 0}">
                                <div class="asset-grid">
                                    <c:forEach items="${repoMetaData}" var="file">
                                        <div class="asset-card">
                                            <div class="asset-icon" style="background-color: #E0F2FE; color: #0284C7;">FILE</div>
                                            <div class="asset-meta">
                                                <h4>${file.fileName}</h4>
                                                <p>${file.fileSize} Bytes • ${file.uploadDate}</p>
                                                <span class="version-badge">v1.0</span>
                                            </div>
                                            <div class="action-menu">⋮</div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div style="text-align: center; padding: 50px; background-color: var(--surface); border-radius: var(--radius-card); border: 2px dashed #E1E4E8;">
                                    <h3 style="color: var(--text-secondary); margin-bottom: 10px;">No assets uploaded yet</h3>
                                    <p style="color: #9CA3AF; font-size: 14px;">Click "+ Upload Asset" to add binary files to this vault.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <aside class="activity-sidebar">
                        <div class="sidebar-section-title">Details</div>
                        <div class="activity-feed">
                            <div class="activity-item">
                                <strong>Created</strong>
                                <span>${repository.creationDate.toLocalDate()}</span>
                            </div>
                            <div class="activity-item">
                                <strong>Root Path</strong>
                                <span style="word-break: break-all; font-family: monospace;">${repository.git_path}</span>
                            </div>
                        </div>

                        <div class="sidebar-section-title" style="margin-top: 30px;">Collaborators</div>
                        <div class="collab-list">
                            <div class="collab-user">
                                <div class="avatar-circle">ME</div>
                                <span>${sessionScope.loggedUser.name} (Owner)</span>
                            </div>
                        </div>
                    </aside>

                </div>
            </div>
        </main>
    </div>

</body>
</html>