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
    <style>
        .file-link {
            text-decoration: none;
            color: inherit;
        }
        .file-link:hover {
            color: #0284C7;
            text-decoration: underline;
        }
    </style>
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
                        
                        <%-- SECCION PARA CODIGO FUENTE (GIT) --%>
                        <c:if test="${fn:length(gitFiles) > 0}">
                            <h3 style="color: var(--text-primary); margin-bottom: 15px; border-bottom: 1px solid #30363d; padding-bottom: 5px;">Source Code</h3>
                            <div class="asset-grid" style="margin-bottom: 30px;">
                                <c:forEach items="${gitFiles}" var="fileName">
                                    <div class="asset-card">
                                        <div class="asset-icon" style="background-color: #f0fdf4; color: #16a34a;">CODE</div>
                                        <div class="asset-meta">
                                            <h4>
                                                <a href="${pageContext.request.contextPath}/Viewer?repositoryId=${repository.idRepository}&file=${fileName}" class="file-link">
                                                    ${fileName}
                                                </a>
                                            </h4>
                                            <span class="version-badge" style="background-color: #dcfce7; color: #166534;">Git Tracked</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <%-- SECCION PARA BINARIOS (LFS) --%>
                        <c:if test="${fn:length(repoMetaData) > 0}">
                            <h3 style="color: var(--text-primary); margin-bottom: 15px; border-bottom: 1px solid #30363d; padding-bottom: 5px;">LFS Binaries</h3>
                            <div class="asset-grid">
                                <c:forEach items="${repoMetaData}" var="file">
                                    <div class="asset-card">
                                        <div class="asset-icon" style="background-color: #E0F2FE; color: #0284C7;">LFS</div>
                                        <div class="asset-meta">
                                            <h4>
                                                ${file.fileName}
                                            </h4>
                                            <p>${file.byteSize} Bytes • ${file.uploadDate}</p>
                                            <span class="version-badge">v1.0</span>
                                        </div>
                                        <div class="action-menu">⋮</div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <%-- MENSAJE SI ESTA VACIO --%>
                        <c:if test="${fn:length(gitFiles) == 0 && fn:length(repoMetaData) == 0}">
                            <div style="text-align: center; padding: 50px; background-color: var(--surface); border-radius: var(--radius-card); border: 2px dashed #E1E4E8;">
                                <h3 style="color: var(--text-secondary); margin-bottom: 10px;">No assets uploaded yet</h3>
                                <p style="color: #9CA3AF; font-size: 14px;">Click "+ Upload Asset" to add code or binaries to this vault.</p>
                            </div>
                        </c:if>
                        
                    </div>

                    <a href="${pageContext.request.contextPath}/DownloadVault?repositoryId=${repository.idRepository}" 
                    class="btn-primary" 
                    style="background-color: #2ea44f; margin-left: 10px; text-decoration: none;">
                    <i class="fas fa-download"></i> Clone / Download ZIP
                    </a>

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

                        <div class="sidebar-section-title" style="margin-top: 30px;">Git History</div>
                        <div class="activity-feed">
                            <c:choose>
                                <c:when test="${fn:length(commitHistory) > 0}">
                                    <c:forEach items="${commitHistory}" var="commit">
                                        <div class="activity-item">
                                            <strong>[${commit.hash}]</strong> ${commit.message}
                                            <span>${commit.author} • ${commit.date}</span>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="activity-item">
                                        <span style="font-style: italic;">No commits yet.</span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
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

    <div id="modalUploadAsset" class="modal-overlay" style="display: none;">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Upload to Vault</h3>
                <button type="button" class="close-btn" onclick="closeUploadModal()">&times;</button>
            </div>
            
            <form action="${pageContext.request.contextPath}/UploadAsset" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="repositoryId" value="${repository.idRepository}">
                
                <div class="form-group">
                    <label for="fileUpload">Select File</label>
                    <input type="file" id="fileUpload" name="fileUpload" required style="padding: 8px;">
                </div>

                <div class="form-group" style="margin-top: 15px;">
                    <label for="bulletPoints">Change Description</label>
                    <textarea id="bulletPoints" name="bulletPoints" rows="3" placeholder="- Se corrigió la geometría&#10;- Nueva versión estable"></textarea>
                </div>
                
                <p style="font-size: 12px; color: var(--text-secondary); margin-bottom: 15px; margin-top: 10px;">
                    * Source code (.c, .h) goes to Git. Binaries (.stl, .pdf) go to LFS Vault.
                </p>
                
                <div class="modal-actions">
                    <button type="button" class="btn-secondary" onclick="closeUploadModal()">Cancel</button>
                    <button type="submit" class="btn-primary">Upload File</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openUploadModal() {
            document.getElementById('modalUploadAsset').style.display = 'flex';
        }
        function closeUploadModal() {
            document.getElementById('modalUploadAsset').style.display = 'none';
        }
    </script>

</body>
</html>