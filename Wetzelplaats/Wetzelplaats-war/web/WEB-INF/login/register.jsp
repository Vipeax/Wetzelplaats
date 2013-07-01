<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="body rounded">
    <div class="register">
        <h3>Register for Wetzelplaats</h3>
         <c:if test="${error != null}">
            <div class="alert alert-error">
                <ul>
                    <c:forEach var="e" items="${error}">
                        <li><c:out value="${e}"/></li>
                        </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="register" method="POST">  
            <div>
                <label for="txtFirstname">
                    First name
                </label>
                <input type="text" name="txtFirstname" value="${firstname}" />
            </div>
            <div>
                <label for="txtLastname">
                    Last name
                </label>
                <input type="text" name="txtLastname" value="${lastname}" />
            </div>
            <div>
                <label for="txtEmail">
                    Email address
                </label>
                <input type="text" name="txtEmail" value="${email}" />
            </div>
            <div>
                <label for="txtPassword">
                    Password
                </label>
                <input type="password" name="txtPassword" value="${password}" />
            </div>
            <div>
                <label for="txtPassword2">
                    Re-enter password
                </label>
                <input type="password" name="txtPassword2" value="${password2}" />
            </div>
            <div>
                <button type="submit" class="btn-link" name="btnRegister" >Register</button>
            </div>
        </form>
        </div>
</div>


