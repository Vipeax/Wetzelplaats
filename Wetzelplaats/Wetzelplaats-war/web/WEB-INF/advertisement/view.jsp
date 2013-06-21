<c:if test="${errors != null}">
    <div class="alert alert-error">
        <ul>
            <c:forEach var="er" items="${errors}">
                <li>${er}</li>
                </c:forEach>
        </ul>
    </div>
</c:if>

<!--<div class="adv-block fll">-->
<div class="container">
    <div class="row">
        <div class="span2 body" style="background-color:#bbd8e9;">
            <div>
                <h3>${ad.name}</h3>
            </div>

            <div class="picture"></div>

            <div class="user">
                <div>
                    <b>Created by:</b>
                </div>
                ${ad.userId.firstname}
            </div>

            <div class="description">
                <div><b>Description:</b></div>
                ${ad.description}
            </div>

            <div class="price">
                <div class="fll">
                    <b>Price:</b>
                    <p>$${ad.price}</p>                            
                </div>
                <div class="flr">
                    <!--<b>Current bid:</b>
                    <p> 
                    <c:choose>
                        <c:when test="${ad.bidCollection.isEmpty()}">
                            N.A.
                        </c:when>
                        <c:otherwise>
                            $${ad.bidCollection[0]}
                        </c:otherwise>
                    </c:choose>
                </p> -->
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="span4 offset3 rounded body">
            <div class="row">
                <h2>Bids</h2>
            </div>
            <div class="row bid-container ">
                <ul>
                    <c:forEach var="bid" items="${ad.bidCollection}">
                        <li><b>$${bid.price}</b> <i class="less">by ${bid.userId.firstname}</i></li>
                        </c:forEach>
                </ul>                
            </div>
            <div class="row">
                <form action="/Wetzelplaats-war/ad/view" method="POST">
                    <input type="hidden" value="${ad.id}" name="ad" />
                    <input type="text" name="txtBid" />
                    <input type="submit" name="btnSubmit" value="Place bid"/>
                </form>
            </div>
        </div>
    </div>
</div>



<div class="clear"></div>