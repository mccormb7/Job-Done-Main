<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>




<sec:authorize access="hasRole('ROLE_TRADE')">
<h1>Search for recommended Jobs within a desired distance</h1>
<form action="journey" method="post">
	Search: <input type="text" name="journeyString" /><br /> 
	<input type="reset" /> <input type="submit" />
</form>
</sec:authorize>



<c:choose>
<c:when test="${hasRecommendation}">
	<p>
	<h1>Providers Recommendation Page</h1>
	</p>
	
	<table class="table table-hover">
		<tr>
			<td>Name</td>
			<td>   </td>
			<td>Details</td>
			<td>Distance</td>
			<td>Date Posted</td>
			<td>Description</td>
			
		</tr>
	
		<c:forEach var="jobpost" items="${recommend}">
			<tr>
	
				<td><c:out value="${jobpost.user.name}"></c:out></td>
		
				<td><a href="<c:url value='/viewjobpost/${jobpost.id}' />" >View Job Post</a></td>
				<td><a href = "<c:url value='/message?uid=${jobpost.user.username}'/>">contact</a></td>
				<td><c:out value="${jobpost.distance} KM"></c:out></td>
				<td><c:out value="${jobpost.date} "></c:out></td>
		
				<td><c:out value="${jobpost.description}"></c:out></td>
	
			</tr>
		</c:forEach>
	</table>
	
</c:when>
	<c:otherwise>
		<p>
		<a>Based on your profile and cirteria, the system has not been able to determine any matches, try and update your profile with more relivant information or search jobs manuely</a>
			<a href="${pageContext.request.contextPath}/profile">Profile</a>
			<a href="${pageContext.request.contextPath}/">Browse All Jobs</a>
		</p>
	</c:otherwise>

</c:choose>






<!-- goes to url and tries to download the data and pass it to the function -->
<script type="text/javascript">
 
function messageCountLink(data){
	$("#messageNumber").text(data.number);
	
}
function pageLoad(){
	pageUpdate();
	window.setInterval(pageUpdate, 5000);
	
}
function pageUpdate(){
	
	$.getJSON("<c:url value="/getmessages"/>", messageCountLink);

}

$(document).ready(pageLoad);
</script>

<!-- goes to url and tries to download the data and pass it to the function -->
<script type="text/javascript">
 
function messageCountLink(data){
	$("#messageNumber").text(data.number);
	
}
function pageLoad(){
	pageUpdate();
	window.setInterval(pageUpdate, 5000);
	
}
function pageUpdate(){
	
	$.getJSON("<c:url value="/getmessages"/>", messageCountLink);

}

$(document).ready(pageLoad);
</script>
