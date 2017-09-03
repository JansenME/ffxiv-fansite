<%@include file="taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="headtags.jsp" %>
    <title>Error</title>
</head>
<body>
<div id="page">
    <div id="header">
        <%@include file="header.jsp" %>
        <ul>
            <li><a href="/index">Home</a></li>
            <li><a href="/members">Members</a></li>
            <li><a href="/calculators">Calculators</a></li>
            <li><a href="/items">Items</a></li>
        </ul>
    </div>
    <div id="content">
        <div>
            <h3 style="text-align:center; margin:auto;">Hey, something went wrong. You're not trying to hack, are
                you?!</h3>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>