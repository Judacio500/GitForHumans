<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Collaborators - Code Vault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body>
    <main class="auth-container">
        <section class="auth-box">
            <h2>Team Access</h2>
            <p>Add collaborators to Project_Sésamo</p>

            <form action="${pageContext.request.contextPath}/collaborators" method="POST">
                <div class="form-group">
                    <label for="collabEmail">Collaborator Email</label>
                    <input type="email" id="collabEmail" name="collabEmail" placeholder="developer@example.com" required>
                </div>
                <button type="submit">Grant Access</button>
            </form>

            <div class="auth-footer">
                <a href="${pageContext.request.contextPath}/repository">Back to Repository</a>
            </div>
        </section>
    </main>
</body>
</html>