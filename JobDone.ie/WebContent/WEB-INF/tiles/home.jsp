<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1> List of Job Posts </h1>
<table class="table table-hover" >
	
	<thead>
		<tr class="danger">
		
			<td>Name</td>
			<td>   </td>
			
			<td>Details</td>
			<td></td>
			<td>   </td>
			<td></td>
			<td></td>
			<td>Description</td>	
		</tr>
	</thead>

	<c:forEach var="jobpost" items="${jobposts1}">
		<tr class="info">
			<td><c:out value="${jobpost.user.name}"></c:out></td>

			<td></td>
			<td><a href="<c:url value='/viewjobpost/${jobpost.id}' />" >View </a></td>
			<td></td>
			<td><a href = "<c:url value='/message?uid=${jobpost.user.username}'/>">contact</a></td>
			<td></td>
			<td><a href = "<c:url value='/emailform?uid=${jobpost.user.email}'/>">Email</a></td>
			
			<td><c:out value="${jobpost.title}"></c:out></td>
	
		</tr>
	</c:forEach>
</table>

<p/>

<p>
<h1> List of providers </h1>
<table class="table table-hover">
	<tr>
		<td></td>
		<td>Name</td>
		<td>   </td>
		<td>Details</td>
		<td>Description</td>
		
	</tr>

	<c:forEach var="provider" items="${providers1}">
		<tr>
			<td><img style= "display:block; " width="100%" height="100%" src="${provider.internetpic}" /></td>
			<td><c:out value="${provider.user.name}"></c:out></td>
			<td><a href="<c:url value='/viewprofile/${provider.id}' />" >View Profile</a></td>
			<td><a href = "<c:url value='/message?uid=${provider.user.username}'/>">contact</a></td>
	
			<td><c:out value="${provider.experience}"></c:out></td>

		</tr>
	</c:forEach>
</table>
</p>

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
