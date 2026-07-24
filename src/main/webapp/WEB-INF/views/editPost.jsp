<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Post</title>
</head>
<body>

    <h2>Edit Post</h2>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/editPost" method="post" enctype="multipart/form-data">

        <input type="hidden" name="id" value="${post.id}">

        <label>Title:</label><br>
        <input type="text" name="title" value="${post.title}" required><br><br>

        <label>Content:</label><br>
        <textarea name="content" rows="6" cols="50"  required>${post.content}</textarea><br><br>


        <c:if test="${not empty post.imagePath}">
            <label>Current Image:</label><br>
            <img src="${pageContext.request.contextPath}/images/${post.imagePath}"
                 alt="${post.title}" style="max-width:200px;"><br><br>
        </c:if>


        <label> replace Image (optional):</label><br>
        <input type="file" name="image" accept="image/*"><br><br>

        <button type="submit">save changes</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/posts">Back to posts</a></p>


   
</body>
</html>