<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Code Vault - Version Control for Humans</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body style="display: block; align-items: stretch; min-height: auto;">

    <header class="landing-header">
        <a href="#" class="landing-logo">
            📦 Code Vault
        </a>
        <nav class="landing-nav">
            <a href="${pageContext.request.contextPath}/login" class="btn-outline">Sign In</a>
            <a href="${pageContext.request.contextPath}/register" class="btn-primary" style="width: auto;">Get Started</a>
        </nav>
    </header>

    <main>
        <section class="hero-section">
            <h1>Version control and cloud storage, finally united.</h1>
            <p>Code Vault bridges the gap between deep technical source control and everyday file management. Build your software, store your assets, and track every change in one beautiful workspace.</p>
            <div class="hero-buttons">
                <a href="${pageContext.request.contextPath}/register" class="btn-primary" style="text-decoration: none; text-align: center;">Create Free Account</a>
                <a href="#features" class="btn-outline">Learn More</a>
            </div>
        </section>

        <section id="features" class="features-section">
            <h2>Why choose Code Vault?</h2>
            <div class="features-grid">
                
                <div class="feature-card">
                    <div class="feature-icon">👩‍💻</div>
                    <h3>Built for Developers</h3>
                    <p>Experience clean, dense views for your source code. Track commits, analyze changes, and manage your repositories with the precision you expect from standard Git tools.</p>
                </div>

                <div class="feature-card">
                    <div class="feature-icon">☁️</div>
                    <h3>Asset Friendly</h3>
                    <p>Stop fighting Git over binary files. Our hybrid cloud approach treats your 3D models, PDFs, and video demonstrations as first-class citizens with an intuitive Drive-like interface.</p>
                </div>

                <div class="feature-card">
                    <div class="feature-icon">🔄</div>
                    <h3>Unified History</h3>
                    <p>Never lose track of your project's evolution. Enjoy seamless version history for both code and heavy assets without the clutter of renamed files.</p>
                </div>

            </div>
        </section>
    </main>

    <footer class="landing-footer">
        <p>&copy; 2026 Code Vault. Version Control for Humans.</p>
    </footer>

</body>
</html>