<%@include file="taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="headtags.jsp" %>
    <title>Create a new item</title>
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
				<form:form method="POST" modelAttribute="itemsForm" class="form-signin">
					<h2 class="form-signin-heading">Add a new item</h2>

					<spring:bind path="runescapeId">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="number" path="runescapeId" class="form-control" placeholder="Runescape ID" required="required" autofocus="true"></form:input>
							<form:errors path="runescapeId"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="experience">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="number" path="experience" class="form-control" placeholder="Experience each" required="required"></form:input>
							<form:errors path="experience"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="levelNeeded">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="number" path="levelNeeded" class="form-control" placeholder="Level needed" required="required"></form:input>
							<form:errors path="levelNeeded"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="skill">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="skill" class="form-control" placeholder="Skill" required="required"></form:input>
							<form:errors path="skill"></form:errors>
						</div>
					</spring:bind>

					<button class="btn btn-lg btn-primary btn-block" type="submit">Versturen</button>

				</form:form>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</div>
</body>
</html>