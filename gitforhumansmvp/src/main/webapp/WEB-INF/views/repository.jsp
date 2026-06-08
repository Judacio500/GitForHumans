<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Repository - Code Vault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body style="align-items: flex-start;">

    <div class="app-layout">
        
        <aside class="sidebar">
            <div class="brand">
                Code Vault
            </div>
            
            <button type="button" class="btn-primary" style="font-size: 14px; padding: 10px 16px;">+ Upload / Create</button>

            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-item active">My Vault</a>
                <a href="#" class="nav-item">Shared with me</a>
                <a href="#" class="nav-item">Starred</a>
            </nav>

            <div class="user-profile">
                <p>Judacio500</p>
                <a href="${pageContext.request.contextPath}/home" style="color: var(--danger-text); font-size: 12px; text-decoration: none; display: block; margin-top: 5px;">Sign Out</a>
            </div>
        </aside>

        <main class="main-wrapper">
            
            <header class="topbar">
                <input type="text" class="search-bar" placeholder="Search files in this repository...">
            </header>

            <div class="content-scroll">
                
                <section class="repo-header">
                    <div class="repo-info">
                        <h2>Project_Sésamo</h2>
                        <div class="storage-widget">
                            <div class="progress-bar">
                                <div class="progress-fill"></div>
                            </div>
                            <span>17.8 MB of 100 MB used</span>
                        </div>
                    </div>
                </section>

                <div class="lens-switcher">
                    <button type="button" class="tab-btn active" id="btn-code">Source Code</button>
                    <button type="button" class="tab-btn" id="btn-assets">Assets & Docs</button>
                </div>

                <div class="repo-container">
                    
                    <div class="repo-left-content">
                        
                        <div class="view-lens active" id="lens-code-view">
                            <div class="list-view">
                                <div class="list-row header">
                                    <div>File Name</div>
                                    <div>Last Message (Commit)</div>
                                    <div>Age</div>
                                </div>

                                <a href="#" class="list-row">
                                    <div style="font-weight: 600; color: var(--accent);">📄 main.c</div>
                                    <div style="color: var(--text-secondary);">Ajuste en la capa oculta de la red neuronal de inferencia local</div>
                                    <div>2 hours ago</div>
                                </a>

                                <a href="#" class="list-row">
                                    <div style="font-weight: 600; color: var(--accent);">📄 esp32_sensor.ino</div>
                                    <div style="color: var(--text-secondary);">Lectura de humedad optimizada y mapeo de pines para TinyML</div>
                                    <div>1 day ago</div>
                                </a>

                                <a href="#" class="list-row">
                                    <div style="font-weight: 600; color: var(--accent);">📄 neuron.h</div>
                                    <div style="color: var(--text-secondary);">Definición estructural de pesos, sesgos y tasa de aprendizaje</div>
                                    <div>3 days ago</div>
                                </a>
                                
                                <a href="#" class="list-row">
                                    <div style="font-weight: 600; color: var(--accent);">📄 weights.bin</div>
                                    <div style="color: var(--text-secondary);">Exportación inicial de coeficientes entrenados en sprint corto</div>
                                    <div>3 days ago</div>
                                </a>
                            </div>
                        </div>

                        <div class="view-lens" id="lens-assets-view">
                            <div class="asset-grid">
                                
                                <div class="asset-card">
                                    <div class="action-menu" title="Version History">...</div>
                                    <div class="asset-icon icon-cad">📐</div>
                                    <div class="asset-meta">
                                        <h4>Estructura_Hidroponica.cad</h4>
                                        <p>Plano estructural de torres</p>
                                        <span class="version-badge">Version 2</span>
                                    </div>
                                </div>

                                <div class="asset-card">
                                    <div class="action-menu" title="Version History">...</div>
                                    <div class="asset-icon icon-pdf">📄</div>
                                    <div class="asset-meta">
                                        <h4>Modelo_Negocios_Instalacion.pdf</h4>
                                        <p>Manual del servicio</p>
                                        <span class="version-badge">Version 1</span>
                                    </div>
                                </div>

                                <div class="asset-card">
                                    <div class="action-menu" title="Version History">...</div>
                                    <div class="asset-icon icon-video">🎥</div>
                                    <div class="asset-meta">
                                        <h4>Prueba_Bomba_Agua.mp4</h4>
                                        <p>Video de validación de flujo</p>
                                        <span class="version-badge">Version 3</span>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                        
                    </div> <aside class="activity-sidebar">
                        <h3 class="sidebar-section-title">Recent Activity</h3>
                        <div class="activity-feed">
                            <div class="activity-item">
                                You uploaded <strong>Version 3</strong> of <code>Prueba_Bomba_Agua.mp4</code>
                                <span>5 hours ago</span>
                            </div>
                            <div class="activity-item">
                                You pushed changes to <code>esp32_sensor.ino</code>
                                <span>1 day ago</span>
                            </div>
                            <div class="activity-item">
                                <strong>darkiman18</strong> downloaded <code>Estructura_Hidroponica.cad</code>
                                <span>2 days ago</span>
                            </div>
                        </div>

                        <h3 class="sidebar-section-title">Collaborators</h3>
                        <div class="collab-list">
                            <div class="collab-user">
                                <div class="avatar-circle">ME</div>
                                <span>Judacio500 (Owner)</span>
                            </div>
                            <div class="collab-user">
                                <div class="avatar-circle" style="background-color: #ECFDF5; color: #10B981;">JD</div>
                                <span>darkiman18</span>
                            </div>
                        </div>
                        
                        <div style="margin-top: 25px; font-size: 13px; text-align: center;">
                            <a href="${pageContext.request.contextPath}/collaborators" style="color: var(--accent); text-decoration: none; font-weight: 700;">+ Manage Access</a>
                        </div>
                    </aside>

                </div> </div> </main>
    </div>

    <script>
        document.getElementById('btn-code').addEventListener('click', function() {
            this.classList.add('active');
            document.getElementById('btn-assets').classList.remove('active');
            document.getElementById('lens-code-view').classList.add('active');
            document.getElementById('lens-assets-view').classList.remove('active');
        });

        document.getElementById('btn-assets').addEventListener('click', function() {
            this.classList.add('active');
            document.getElementById('btn-code').classList.remove('active');
            document.getElementById('lens-assets-view').classList.add('active');
            document.getElementById('lens-code-view').classList.remove('active');
        });
    </script>
</body>
</html>