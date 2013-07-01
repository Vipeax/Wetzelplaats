<%-- 
    Document   : index
    Created on : Jun 20, 2013, 9:07:04 PM
    Author     : Timo
--%>

<div class="body rounded">
    <!--<div class="ad-container"> -->
    <div class="row-fluid">
        <c:choose>
            <c:when test="${adCount <= 0}">
                <p>No ads found. How about you <a href="ad/create">create an ad</a>?</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="ad" items="${ads}">
                    <!--<div class="adv-block fll"> -->
                    <div class="span3">
                        <h3><a href="/Wetzelplaats-war/ad/view?id=${ad.id}">${ad.name}</a></h3>

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
                <!--<div class="clear"></div> -->

                <div class="row">
                    <div class="span12 paging">
                        <c:forEach begin="0" end="${adCount / 4}" varStatus="loop">
                            <c:choose>
                                <c:when test="${loop.index == 0 && (p == null || p == 0)}">
                                    ${loop.index + 1}
                                </c:when>
                                <c:when test="${loop.index != 0 && (p != null && p == loop.index)}">
                                    ${loop.index + 1}
                                </c:when>
                                <c:otherwise>
                                    <a href="/Wetzelplaats-war/index?p=${loop.index}">${loop.index + 1}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>