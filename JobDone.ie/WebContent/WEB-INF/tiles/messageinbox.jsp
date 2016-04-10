<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
  
  
  
  message inbox
  
  
  <h1> List of Messages </h1>
<table class="offers">
	<tr>
		<td>Name</td>
		<td>Date</td>
		<td>	</td>
		<td>	</td>
		<td>  Subject </td>
		<td>Their Username</td>
		<td>Your Username</td>
		
	</tr>

	<c:forEach var="messages" items="${messages1}">
		<tr>

			<td><c:out value="${messages.name}"></c:out></td>
			
			<td><a href="<c:url value='/deletemessage/${messages.id}' />" >Delete</a></td>
			<td><a href = "<c:url value='/message?uid=${messages.fromuser}'/>">Reply</a></td>
			<td><c:out value="${messages.subject}"></c:out></td>
			<td><c:out value="${messages.fromuser}"></c:out></td>
			<td><c:out value="${messages.username}"></c:out></td>

		</tr>
	</c:forEach>
</table>