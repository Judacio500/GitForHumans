<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Code Vault</title>
</head>
<body>

    <main class="auth-container">
        <section class="auth-box">
            <h2>Create an Account</h2>
            <p>Join Code Vault to manage your repositories</p>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-alert">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/register" method="POST">
                
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" required>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" required autocomplete="email">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit">Register</button>
            </form>

            <div class="auth-footer">
                <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in here</a></p>
            </div>
        </section>
    </main>

</body>
</html>