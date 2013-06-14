<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="create">
    <h2>Create an advertisement!</h2>
    <form action="/Wetzelplaats-war/ad/create" method="POST">
        <c:if test="${created}">
            <div class="message succes">
                Successfully created the advertisement. <a href="/Wetzelplaats-war/index">Click here</a> to return to the home page.
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
            <input type="submit" value="Create" name="btnCreate" />
        </div>
    </form>
</div>