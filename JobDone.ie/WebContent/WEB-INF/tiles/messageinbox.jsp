<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
  
  
  
  message inbox
  
  
  <h1> List of Job Posts </h1>
<table class="offers">
	<tr>
		<td>Name</td>
		<td>	</td>
		<td>	</td>
		<td>  Subject </td>
		<td>Your Email</td>
		<td>Username being sent to</td>
		
	</tr>

	<c:forEach var="messages" items="${messages1}">
		<tr>

			<td><c:out value="${messages.name}"></c:out></td>
			<td><a href="<c:url value='/deletemessage/${messages.id}' />" >Delete</a></td>
			<td><a href = "<c:url value='/message?uid=${messages.username}'/>">Reply</a></td>
			<td><c:out value="${messages.subject}"></c:out></td>
			<td><c:out value="${messages.email}"></c:out></td>
			<td><c:out value="${messages.username}"></c:out></td>
			<!--<td><a href="<c:url value='/deletemessage/${jobpost.id}' />" >Delete</a></td>
			<td><a href="<c:url value='/editjobpost/${jobpost.id}' />" >Edit</a></td>
			  <td><a href="${pageContext.request.contextPath}/editjobpost">Edit</a></td>-->
			<!--  <td><a href="<c:url value='/viewjobpost/${jobpost.id}' />" >View Job Post</a></td>
			<td><a href = "<c:url value='/message?uid=${jobpost.user.username}'/>">contact</a></td>
	
			<td><c:out value="${jobpost.description}"></c:out></td>-->

		</tr>
	</c:forEach>
</table>