<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="body rounded">
    <div class="login">
        <h3>Login here!</h3>
        <c:if test="${registered}">
            <div class="alert alert-success">
                <ul>
                    <li>Successfully registered! Please login here!</li>
                </ul>
            </div>
        </c:if>
        <c:if test="${error != null}">
            <div class="alert alert-error">
                <ul>
                    <c:forEach var="e" items="${error}">
                        <li><c:out value="${e}"/></li>
                        </c:forEach>
                </ul>
            </div>
        </c:if>
    
        <form action="login" method="POST">    
            <div>
                <label for="txtEmail">
                    Email
                </label>
                <input type="text" name="txtEmail">
            </div>
            <div>
                <label for="txtPassword">
                    Password
                </label>
                <input type="password" name="txtPassword">
            </div>
            <div>
                <button class="btn-link" type="submit" value="Login">Login</button>     
                <a href="register">Register</a>
            </div>
        </form>
    </div>
</div>

