<div class="action-menu">
    <a href="ad/create">Create ad</a>
</div>

<div class="ad-container">
    <c:choose>
        <c:when test="${adCount == 0}">
            <p>Er zijn helaas geen advertenties op dit moment :-(</p>
        </c:when>
        <c:otherwise>
            <!--  Loop through advertisements-->
        </c:otherwise>
    </c:choose>
</div>

