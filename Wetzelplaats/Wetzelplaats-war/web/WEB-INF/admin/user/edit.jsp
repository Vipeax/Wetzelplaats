<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   
    <div class="body rounded">               
        <div class="edit">
            <h2>Edit user</h2>
            
            <c:if test="${success}">
                <div class="alert alert-success">
                    <ul>
                        <li>Changes saved!</li>
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
            <form action="edit" method="POST">
                <input type="hidden" value="${olduser.id}" name="userid" />
                <div>
                <label for="txtFirstname">
                    First name
                </label>
                <input type="text" name="txtFirstname" value="${olduser.firstname}" />        
                </div>
                <div>
                    <label for="txtLastname">
                        Last name
                    </label>
                    <input type="text" name="txtLastname" value="${olduser.lastname}" />
                </div>
                <div>
                    <label for="txtEmail">
                        Email address
                    </label>
                    <input type="text" name="txtEmail" value="${olduser.email}" />
                </div>
                <div>
                    <label for="txtPassword">
                        Password
                    </label>
                    <input type="password" name="txtPassword">
                </div>
                <div>
                    <label for="txtPassword2">
                        Repeat password
                    </label>
                    <input type="password" name="txtPassword2">
                </div>
                <div>
                   <label for="userType">
                        Role
                    </label>
                    <select name="userType">
                        <option value ="User">User</option>
                        <option value ="Admin">Admin</option>
                    </select>
                </div>                
                <div>
                    <input type="submit" value="Save changes" name="btnSaveChanges" class="btn-link"/> <a href="/Wetzelplaats-war/admin">Cancel</a>
                </div>

            </form>
        </div>
    </div>

