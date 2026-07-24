<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${post.title}</title>
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

    <p><a href="${pageContext.request.contextPath}/posts">Back to all posts</a></p>

    <hr>

    <div>

        <h1>${post.title}</h1>

        <p><small>By ${post.user.username} on ${post.fullDateDisplay}</small></p>

       

        <c:if test="${not empty post.imagePath}">
            <img src="${pageContext.request.contextPath}/images/${post.imagePath}"
                alt="${post.title}" style="max-width:500px;"><br><br>
        </c:if>

        <div>
              <c:if test="${sessionScope.userId == post.user.id}">
                <a href="${pageContext.request.contextPath}/editPost?id=${post.id}" role="button" >Edit</a>
            </c:if>

            &nbsp;&nbsp;

        
            <c:if test="${sessionScope.userId == post.user.id}">
                <form id="dltform" action="${pageContext.request.contextPath}/deletePost" method="post" style="display: inline;">
                    <input type="hidden" name="id" value="${post.id}">
                    <button class="dltbttn" type="submit"><b>Delete</b></button>
                </form>
            </c:if>
        </div>

        <p>${post.content}</p>

    </div>

    
    <script>
        document.querySelector("#dltform").addEventListener("submit" , (event)=>{
            event.preventDefault();

            if(confirm("sure to delete this post")){
               event.target.submit();
            }
        })  
    </script>

    
</body>
</html>