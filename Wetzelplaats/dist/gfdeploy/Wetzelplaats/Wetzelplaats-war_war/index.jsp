<div class="action-menu">
    <a href="ad/create">Create ad</a>
</div>

<div class="ad-container">
    <c:choose>
        <c:when test="${adCount == 0}">
            <p>Er zijn helaas geen advertenties op dit moment :-(</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="ad" items="${ads}">
                <div class="adv-block fll">
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
            <div class="clear"></div>

            <div class="paging">

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
        </c:otherwise>
    </c:choose>
</div>

