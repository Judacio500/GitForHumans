<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${fileName} - Code Vault</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/c.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/cpp.min.js"></script>
    
    <script>hljs.highlightAll();</script>

    <style>
        body { background-color: #0d1117; color: #c9d1d9; font-family: sans-serif; margin: 0; padding: 20px; }
        .code-container { border: 1px solid #30363d; border-radius: 6px; overflow: hidden; }
        .code-header { background: #161b22; padding: 12px 16px; border-bottom: 1px solid #30363d; display: flex; justify-content: space-between; align-items: center; }
        .code-header span { font-family: monospace; font-size: 14px; font-weight: bold; }
        .back-btn { color: #58a6ff; text-decoration: none; font-size: 14px; }
        .back-btn:hover { text-decoration: underline; }
        pre { margin: 0; }
        code { font-family: 'Courier New', Courier, monospace; font-size: 14px; padding: 16px !important; }
    </style>
</head>
<body>

    <div class="code-container">
        <div class="code-header">
            <span>📄 ${fileName}</span>
            <a href="${pageContext.request.contextPath}/repository?id=${repositoryId}" class="back-btn">← Volver al Repo</a>
        </div>
        
        <pre><code class="language-c"><c:out value="${fileContent}"/></code></pre>
    </div>

</body>
</html>