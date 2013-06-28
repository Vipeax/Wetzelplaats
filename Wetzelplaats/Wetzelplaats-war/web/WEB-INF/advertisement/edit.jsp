<%-- 
    Document   : account
    Created on : Jun 28, 2013, 12:25:04 PM
    Author     : Robert J
--%>
<c:if test="${errors != null}">
    <div class="alert alert-error">
        <ul>
            <c:forEach var="er" items="${errors}">
                <li>${er}</li>
                </c:forEach>
        </ul>
    </div>
</c:if>
<div class="body rounded">
    <!--<div class="ad-container"> -->
    <div class="row-fluid">
        <h3>Edit advertisement</h3>

        <div class="row">
            <div class="span8 offset1">
                <form action="/Wetzelplaats-war/ad/edit" method="POST">
                    <input type="hidden" value="${ad.id}" name="ad" />
                    <div>
                        <label>Title:</label><input type="text" value="${ad.name}" name="txTitle" /><br/>
                    </div>
                    <div>
                        <label>Description:</label><input type="text" value="${ad.description}" name="txtDescription" /><br/>
                    </div>
                    <div>
                        <label>Price:</label><input type="text" value="${ad.price}" name="txtPrice" /><br/>
                    </div>
                    <input type="submit" name="btnSubmit" value="Edit"/>
                </form> 
            </div>
        </div>
    </div>
</div>