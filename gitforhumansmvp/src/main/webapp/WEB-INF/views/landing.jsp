<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code Vault - Version Control for Humans</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body>
    <main class="auth-container">
        <section class="auth-box">
            <h2>Code Vault</h2>
            <p>Your secure, intuitive cloud folder for code.</p>
            <a href="${pageContext.request.contextPath}/login">
                <button type="button">Sign In</button>
            </a>
            <div class="auth-footer">
                <a href="${pageContext.request.contextPath}/register">Create an Account</a>
            </div>
        </section>
    </main>
</body>
</html>