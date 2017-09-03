<%@include file="taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="headtags.jsp" %>
    <title>Runescape - Items</title>
</head>
<body>
<div id="page">
    <div id="header">
        <%@include file="header.jsp" %>
        <ul>
            <li><a href="/index">Home</a></li>
            <li><a href="/members">Members</a></li>
            <li><a href="/calculators">Calculators</a></li>
            <li class="current"><a href="/items">Items</a></li>
        </ul>
    </div>
    <div id="content">
        <div>
            <h3 style="text-align:center; width:50%; margin:auto;">Item list</h3>
            <table class="table table-striped" style="text-align:center; width:50%; margin:auto;">
                <tbody>
                <c:forEach var="listValue" items="${itemList}">
                    <tr>
                        <td><a href='/item/${listValue.id}'>${listValue.nameItem}</a></td>
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