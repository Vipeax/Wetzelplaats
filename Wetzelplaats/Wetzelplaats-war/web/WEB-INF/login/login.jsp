<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="login" method="POST">
    <h2>Login here!</h2>
    <c:if test="${registered}">
        <div class="message success">
            <ul>
                <li>Successfully registered! Please login here!</li>
            </ul>
        </div>
    </c:if>
    <c:if test="${error != null}">
        <div class="message error">
            <ul>
                <c:forEach var="e" items="${error}">
                    <li><c:out value="${e}"/></li>
                    </c:forEach>
            </ul>
        </div>
    </c:if>
    <div>
        <label for="txtEmail">
            Email:
        </label>
        <input type="text" name="txtEmail">
    </div>
    <div>
        <label for="txtPassword">
            Password:
        </label>
        <input type="password" name="txtPassword">
    </div>
    <div>
        <input type="submit" value="Login"/>
    </div>
    <div>
        <a href="register">register</a>
    </div>
</form>