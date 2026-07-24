<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Blog Posts</title>
</head>
<body>

    <h1>Blog Posts</h1>
    
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty sessionScope.username}">
            <p>Welcome, ${sessionScope.username}!
               <a href="${pageContext.request.contextPath}/logout">Logout</a> |
               <a href="${pageContext.request.contextPath}/newPost">Create Post</a>
            </p>
        </c:when>
        <c:otherwise>
            <p>
                <a href="${pageContext.request.contextPath}/login">Login</a> |
                <a href="${pageContext.request.contextPath}/register">Register</a>
            </p>
        </c:otherwise>
    </c:choose>

    <hr>

    <c:if test="${empty posts}">
        <p>No posts yet.</p>
    </c:if>

    <c:forEach var="post" items="${posts}">
    

        <div style="max-width: 100%; overflow: hidden; padding: 1rem; border: 1px solid #ccc; margin-bottom: 10px; display: flex; flex-direction: row; gap: 2rem;"> 
        
            <div style="padding: 2px; flex-shrink: 0;">
                ${post.monthYearDisplay}
            </div> 
            
            <div style="padding: 2px; flex-grow: 1; min-width: 0;"> 
                <p style="font-size: larger; margin: 0px; margin-bottom: 0.5rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"> 
                    <a href="${pageContext.request.contextPath}/blogPost?id=${post.id}" style="text-decoration: none; color: black;"> ${post.title} </a> 
                </p> 
                <p style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; margin: 0px;"> 
                    ${post.content} 
                </p> 
            </div> 

        </div>


    </c:forEach>


   
</body>
</html>