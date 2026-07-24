<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Post</title>
</head>
<body>

    <h2>Create New Post</h2>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if> 

    <form action="${pageContext.request.contextPath}/newPost" method="post" enctype="multipart/form-data">

        <label>Title:</label><br>
        <input type="text" name="title" required><br><br>

        <label>Content:</label><br>
        <textarea name="content" rows="6" cols="50" required></textarea><br><br>

        <label>Image:</label><br>
        <input type="file" name="image" accept="image/*"><br><br>

        <button type="submit">Publish</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/posts">Back to posts</a></p>

</body>
</html>