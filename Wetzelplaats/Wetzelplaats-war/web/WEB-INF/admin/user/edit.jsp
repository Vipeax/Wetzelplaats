<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2>Register for Wetzelplaats</h2>
<form action="register" method="POST">
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
        <label for="txtFirstname">
            First name
        </label>
        <input type="text" name="txtFirstname" value="${user.firstname}" />
    </div>
    <div>
        <label for="txtLastname">
            Last name
        </label>
        <input type="text" name="txtLastname" value="${user.lastname}" />
    </div>
    <div>
        <label for="txtEmail">
            Email address
        </label>
        <input type="text" name="txtEmail" value="${user.email}" />
    </div>
</form>
