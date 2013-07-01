<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="body rounded">
    <div class="create">
        <h2>Create an advertisement</h2>
        
            <c:if test="${created}">
                <div class="alert alert-success">
                    Successfully created the advertisement. <a href="/Wetzelplaats-war/index">Click here</a> to return to the home page.
                </div>
            </c:if>
            <c:if test="${errors != null}">
                <div class="alert alert-error">
                    <ul>
                        <c:forEach var="e" items="${errors}">
                            <li>"${e}"</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        <form action="/Wetzelplaats-war/ad/create" method="POST">
            <div>
                <label for="txtTitle">
                    Title
                </label>
                <input type="text" name="txtTitle" value="${title}" />
            </div>
            <div>
                <label for="txtDescription">
                    Description
                </label>
                <input type="text" name="txtDescription" value="${description}" />
            </div>
            <div>
                <label for="txtEmail">
                    Price
                </label>
                <input type="text" name="txtPrice" value="${price}" />
            </div>
            <div>
                <input type="submit" value="Create" name="btnCreate" class="btn-link"/>
            </div>
        </form>
    </div>
</div>