<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>main</title>
</head>
<body>
<h2>${sessionScope.loginEmail} 님 환영합니다.</h2>
<button onclick="update()">내정보 수정하기</button>
<button onclick="logout()">로그아웃</button>
<c:if test="${sessionScope.loginEmail eq 'admin'}">
    <button onclick="list()">회원목록 조회</button>
</c:if>
<a href="/board/index">글쓰기</a>
</body>
<script>
    const update = () => {
        location.href = "/member/update";
    }
    const logout = () => {
        location.href = "/member/logout";
    }
    const list = () => {
        location.href = "/member/list";
    }
</script>
</html>