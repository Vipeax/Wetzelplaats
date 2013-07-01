<%-- 
    Document   : account
    Created on : Jun 28, 2013, 12:25:04 PM
    Author     : Robert J
--%>


<div class="body rounded">
    <!--<div class="ad-container"> -->
    <div class="edit">
        <h3>Edit advertisement</h3>
        <c:if test="${errors != null}">
            <div class="alert alert-error">
                <ul>
                    <c:forEach var="er" items="${errors}">
                        <li>${er}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

                <form action="/Wetzelplaats-war/ad/edit" method="POST">
                    <input type="hidden" value="${ad.id}" name="ad" />
                    <div>
                        <label>Title</label><input type="text" value="${ad.name}" name="txTitle" /><br/>
                    </div>
                    <div>
                        <label>Description</label><input type="text" value="${ad.description}" name="txtDescription" /><br/>
                    </div>
                    <div>
                        <label>Price</label><input type="text" value="${ad.price}" name="txtPrice" /><br/>
                    </div>
                    <div>
                        <label>Is sold</label><input type="checkbox" name="isSold">
                    </div>
                    
                    
                    <input type="submit" value="Save changes" name="btnSaveChanges" class="btn-link"/>   <a href="/Wetzelplaats-war/account" >Cancel</a>
                </form> 

    </div>
</div>