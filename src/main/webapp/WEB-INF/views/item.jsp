<%@include file="taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="headtags.jsp" %>
    <title>${itemName}</title>
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
				<table style="border:0px; width:100%;">
					<tr>
						<td style="width:80%; margin:auto; text-align:center;">
							<h3>${itemName}</h3>
							<p style="text-align:center; margin:auto;">${itemDescription}</p>
						</td>
						<td style="width:20%; margin:auto; text-align:right;">
							<img src="${itemIconLarge}" />
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<table style="border:0px; width:100%;">
								<tr>
									<td style="text-align:center; width:50%;">
										<table style="border:0px; width:80%; border-collapse:collapse; margin:auto; vertical-align:text-top;">
											<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Type:</td><td style="text-align:left;">${itemType}</td></tr>
											<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Experience:</td><td style="text-align:left;">${itemExperience}</td></tr>
											<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Level needed:</td><td style="text-align:left;">${itemLevelNeeded}</td></tr>
											<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Skill:</td><td style="text-align:left;">${itemSkill}</td></tr>
										</table>
									</td>
									<td style="text-align:center; width:50%;">
										<table style="border:0px; width:80%; border-collapse:collapse; margin:auto; vertical-align:text-top;">
											<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Current price:</td><td style="text-align:left;">${itemPrice}</td></tr>

											<c:choose>
												<c:when test="${itemTodayTrend}">
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Today price change:</td><td style="text-align:left; color:green;">${itemTodayPrice}</td></tr>
												</c:when>
												<c:otherwise>
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Today price change:</td><td style="text-align:left; color:red;">${itemTodayPrice}</td></tr>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${itemDay30Trend}">
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Past 30 days:</td><td style="text-align:left; color:green;">${itemDay30Change}</td></tr>
												</c:when>
												<c:otherwise>
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Past 30 days:</td><td style="text-align:left; color:red;">${itemDay30Change}</td></tr>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${itemDay90Trend}">
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Past 90 days:</td><td style="text-align:left; color:green;">${itemDay90Change}</td></tr>
												</c:when>
												<c:otherwise>
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Past 90 days:</td><td style="text-align:left; color:red;">${itemDay90Change}</td></tr>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${itemDay180Trend}">
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Past 180 days:</td><td style="text-align:left; color:green;">${itemDay180Change}</td></tr>
												</c:when>
												<c:otherwise>
													<tr style="border-bottom: 1px solid #c80708;"><td style="padding:5px; text-align:right; font-weight:bold;">Past 180 days:</td><td style="text-align:left; color:red;">${itemDay180Change}</td></tr>
												</c:otherwise>
											</c:choose>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</div>
</body>
</html>