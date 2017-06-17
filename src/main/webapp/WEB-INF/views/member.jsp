<%@include file="taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="headtags.jsp" %>
    <title>${memberName}</title>
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
				<table style="width:100%;">
					<tr>
						<td style='padding:5px; text-align:right; width:20%;'><img src="http://services.runescape.com/m=avatar-rs/${memberName}/chat.png" /></td>
						<td style='padding:5px; text-align:center; width:60%;'><h3>${memberName}</h3></td>
						<td style='padding:5px; text-align:left; width:20%;'><img src="http://services.runescape.com/m=avatar-rs/${memberName}/full.png" /></td>
					</tr>
				</table>

				<c:if test="${showTableMember}">
					<table style='width:100%;'>
						<tr>
							<td style='width:60%; padding:5px; text-align:justify; text-align-last:center; vertical-align: text-top;'>${tableInfo.biography}</td>
							<td>
								<table>
									<tr><td style='padding:5px; text-align:right;'><strong>Gender: </strong></td><td style='padding:5px;'>${tableInfo.gender}</td></tr>
									<tr><td style='padding:5px; text-align:right;'><strong>Date of Birth: </strong></td><td style='padding:5px;'>${tableInfo.dob}</td></tr>
									<tr><td style='padding:5px; text-align:right;'><strong>City: </strong></td><td style='padding:5px;'>${tableInfo.cityStateCountry}</td></tr>
								</table>
							</td>
						</tr>
					</table>
				</c:if>

				<table style="border:0px; margin:auto; width:100%; text-align:center;">
					<tr>
						<td style="padding:5px; width:50%;">
							<div id="clickAdventurersLog">
								<h1 style="text-decoration:underline; cursor:pointer;">Adventurers Log</h1>
							</div>
						</td>
						<td style="padding:5px; width:50%;">
							<div id="clickSkills">
								<h1 style="text-decoration:underline; cursor:pointer;">Skills</h1>
							</div>
						</td>
					</tr>
				</table>

				<div id="popupAdventurersLog" style="display: none; margin:auto;">
					<h3 style="text-align: center;">Adventurers Log</h3>
					<table class="table table-striped">
						<thead><tr><td> </td></tr></thead>
						<tbody>
							<c:forEach var="listValue" items="${listAdventurersLog}">
								<tr><td><p style='font-weight:bold;'>${listValue.formattedDate} - ${listValue.title}</p>${listValue.description}</td></tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div id="popupSkills" style="display: none; margin:auto;">
					<h3 style="text-align: center;">Skills</h3>
					<table class="table table-striped">
						<thead><tr><th width="3%"></th><th>Skill</th><th>Level</th><th style='text-align:right;'>Experience</th><th style='text-align:right;'>Rank</th></tr></thead>
						<tbody>
							<c:forEach var="listValue" items="${listLevels}">
								${listValue}
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	<%@include file="footer.jsp" %>
	</div>
<script>
	var clickAdventurersLog = document.getElementById('clickAdventurersLog');
	var clickSkills = document.getElementById('clickSkills');

    clickAdventurersLog.onclick = function () {
        document.getElementById('popupAdventurersLog').style.display = 'block';
        document.getElementById('popupSkills').style.display = 'none';
    };

    clickSkills.onclick = function () {
        document.getElementById('popupAdventurersLog').style.display = 'none';
        document.getElementById('popupSkills').style.display = 'block';
    };
</script>
</body>
</html>