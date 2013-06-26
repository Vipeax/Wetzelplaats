<%-- 
    Document   : my-ads
    Created on : Jun 26, 2013, 11:22:46 AM
    Author     : Robert
--%>
<div class="body rounded">
    <!--<div class="ad-container"> -->
    <div class="row-fluid">
        <h3>Ad management</h3>
        <c:choose>
            <c:when test="${adCount == 0}">
                <p>You do not have any ads yet. How about you <a href="/Wetzelplaats-war/ad/create">create an ad</a>?</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="ad" items="${ads}">
                    <!--<div class="adv-block fll"> -->
                    <div class="span3">
                        <h3><a href="/Wetzelplaats-war/ad/view?id=${ad.id}">${ad.name}</a></h3>
                        <a href="/Wetzelplaats-war/ad/delete?did=${ad.id}&admin=0">Delete</a>

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
                                        <c:when test="${ad.bidCollection.isEmpty()}">
                                            N.A.
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
<br/>
<div class="body rounded">
    <!--<div class="ad-container"> -->
    <div class="row-fluid">
        <h3>Bid management</h3>
        <c:choose>
            <c:when test="${bidCount == 0}">
                <p>You do not have any bids yet. How about you <a href="/Wetzelplaats-war/">start bidding</a>?</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="bid" items="${bids}">
                    <!--<div class="adv-block fll"> -->
                    <div class="span3">
                        <h3><a href="/Wetzelplaats-war/ad/view?id=${bid.advertisementId.id}">${bid.advertisementId.name}</a></h3>
                        ${bid.price}
                    </div>
                </c:forEach>            
                <!--<div class="clear"></div> -->

                <div class="row">
                    <div class="span12 paging">
                        <c:forEach begin="0" end="${bidCount / 4}" varStatus="loop">
                            <c:choose>
                                <c:when test="${loop.index == 0 && (p == null || p == 0)}">
                                    ${loop.index + 1}
                                </c:when>
                                <c:when test="${loop.index != 0 && (p != null && p == loop.index)}">
                                    ${loop.index + 1}
                                </c:when>
                                <c:otherwise>
                                    <a href="/Wetzelplaats-war/account?p=${loop.index}">${loop.index + 1}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
