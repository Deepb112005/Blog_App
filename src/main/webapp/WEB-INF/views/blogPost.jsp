<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${post.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>

    <div class="navbar">
        <div class="container">
            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <span><strong>Welcome, ${sessionScope.username}!</strong></span>
                    <span>
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        <a href="${pageContext.request.contextPath}/newPost">Create Post</a>
                    </span>
                </c:when>
                <c:otherwise>
                    <span></span>
                    <span>
                        <a href="${pageContext.request.contextPath}/login">Login</a>
                        <a href="${pageContext.request.contextPath}/register">Register</a>
                    </span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="container">

        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <p><a href="${pageContext.request.contextPath}/posts">Back to all posts</a></p>

        <div class="post-card">

            <h1>${post.title}</h1>

            <p class="post-meta">By ${post.user.username} on ${post.fullDateDisplay}</p>

            <c:if test="${not empty post.imagePath}">
                <img src="${pageContext.request.contextPath}/images/${post.imagePath}"
                     alt="${post.title}">
            </c:if>

            <p>${post.content}</p>

            <div class="post-actions">
                <c:if test="${sessionScope.userId == post.user.id}">
                    <a href="${pageContext.request.contextPath}/editPost?id=${post.id}">Edit</a>

                    <form id="dltform" action="${pageContext.request.contextPath}/deletePost" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${post.id}">
                        <button type="submit">Delete</button>
                    </form>
                </c:if>
            </div>

        </div>

    </div>

    <script>
        document.querySelector("#dltform").addEventListener("submit", (event) => {
            event.preventDefault();

            if (confirm("Sure to delete this post?")) {
                event.target.submit();
            }
        });
    </script>

</body>
</html>