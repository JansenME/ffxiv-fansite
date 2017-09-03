<%@include file="taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="headtags.jsp" %>
    <title>Laputa Island - Members</title>
</head>
<body>
<div id="page">
    <div id="header">
        <%@include file="header.jsp" %>
        <ul>
            <li><a href="/index">Home</a></li>
            <li class="current"><a href="/members">Members</a></li>
            <li><a href="/calculators">Calculators</a></li>
            <li><a href="/items">Items</a></li>
        </ul>
    </div>
    <div id="content">
        <div>
            <h3>Clanmembers</h3>
            <p>Total clan experience: ${totalExperience}</p>
            <p>Total amount members: ${amountMembers}</p>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th width='30%'>Name</th>
                    <th width='30%'>Rank</th>
                    <th width='30%'>Experience since joined clan</th>
                    <th width='10%'>PvP kills</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="listValue" items="${list}">
                    <tr>
                        <td><a href='member/${listValue.name}'>${listValue.name}</a></td>
                        <td>${listValue.rank}</td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${listValue.experience}"/></td>
                        <td>${listValue.kills}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>