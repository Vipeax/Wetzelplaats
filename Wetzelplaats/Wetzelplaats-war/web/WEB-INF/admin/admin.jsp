<%-- 
    Document   : admin
    Created on : Jun 27, 2013, 9:07:41 PM
    Author     : Robert
    Modified   : R. Wetzels
--%>

<c:if test="${success}">
    <div class="alert alert-success">
        <ul>
            <li>Changes saved!</li>
        </ul>
    </div>
</c:if>

<div class="body rounded">
    <div class="row-fluid">               
        <h3>Ad management</h3>
        <c:choose>
            <c:when test="${adCount <= 0}">
                <p>No ads yet, go get some users you lazy admin!</p>
            </c:when>
            <c:otherwise>
                <div class="row-fluid">
                <c:forEach var="ad" items="${ads}">
                    <!--<div class="adv-block fll"> -->
                    <div class="span3">
                        <h3><a href="/Wetzelplaats-war/ad/view?id=${ad.id}">${ad.name}</a></h3>
                        <a href="/Wetzelplaats-war/ad/delete?did=${ad.id}">Delete</a>

                        <div class="picture"></div>

                        <div class="description">
                            <div><b>Description:</b></div>
                            <c:choose>
                                <c:when test="${ad.description.length() > 40}">
                                    ${ad.description.substring(0, 40)}...
                                </c:when>
                                <c:otherwise>
                                    ${ad.description}
                                </c:otherwise>
                            </c:choose>

                        </div>

                        <div class="price">
                            <div class="fll">
                                <b>Price:</b>
                                <p>$${ad.price}</p>                            
                            </div>
                            <div class="flr">
                                <b>Current bid:</b>
                                <p> 
                                    <c:choose>
                                        <c:when test="${ad.isSold}">
                                            <img src="/Wetzelplaats-war/resources/img/icon-sold.png" alt="Sold" height="48" width="64">
                                        </c:when>
                                        <c:when test="${ad.bidCollection.isEmpty()}">
                                            No bids yet
                                        </c:when>      
                                        <c:otherwise>
                                            $${ad.bidCollection.get(0).price}
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                </c:forEach>    
                    </div>
                <!--<div class="clear"></div> -->

                <div class="row">
                    <div class="span12 paging">
                        <c:forEach begin="0" end="${adCount / 4}" varStatus="loop">
                            <c:choose>
                                <c:when test="${loop.index == 0 && (pa == null || pa == 0)}">
                                    ${loop.index + 1}
                                </c:when>
                                <c:when test="${loop.index != 0 && (pa != null && pa == loop.index)}">
                                    ${loop.index + 1}
                                </c:when>
                                <c:otherwise>
                                    <a href="/Wetzelplaats-war/admin?pu=${pu}&pa=${loop.index}">${loop.index + 1}</a>                                    
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<br/>
<div class="body rounded">
    <div class="row-fluid">
        <h3>User management</h3>
        <div class="row-fluid">
        <c:forEach var="user" items="${users}">
            <!--<div class="adv-block fll"> -->
            <div class="span3">
                <b>${user.firstname} ${user.lastname}</b><br/>
                <i>${user.email}</i>
                <c:choose>
                <c:when test="${currentUser!=user.id}">
                <a href="/Wetzelplaats-war/user/delete?did=${user.id}">Delete</a>
                </c:when>
                </c:choose>
                <a href="/Wetzelplaats-war/user/edit?id=${user.id}">Edit</a>
            </div>
        </c:forEach>   
            </div>
        <!--<div class="clear"></div> -->

        <div class="row">
            <div class="span12 paging">
                <c:forEach begin="0" end="${userCount/ 4}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index == 0 && (pu == null || pu == 0)}">
                            ${loop.index + 1}
                        </c:when>
                        <c:when test="${loop.index != 0 && (pu != null && pu == loop.index)}">
                            ${loop.index + 1}
                        </c:when>
                        <c:otherwise>
                            <a href="/Wetzelplaats-war/admin?pu=${loop.index}&pa=${pa}">${loop.index + 1}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>                           
            </div>
        </div>
    </div>
</div>
