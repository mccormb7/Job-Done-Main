<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	function onDeleteClick(event) {
		var doDelete = confirm("Are you sure youwant to delete this offer?");
		if (doDelete == false) {
			event.preventDefault();
		}
	}

	function onReady() {
		$("#delete").click(onDeleteClick);
	}
	$(document).ready(onReady);
</script>



	<div class="CSSTableGenerator">
		<h1>Message Inbox</h1>
		<table>

			<tr>
				<td>From</td>
				<td>Actions</td>
				<td>Subject</td>
				
				<td>Content</td>
			</tr>


			<c:forEach var="messages" items="${messages1}">
				<tr>
					<td><span class="glyphicon glyphicon-star-empty"><c:out
								value="${messages.fromuser}"></c:out></span></td>
					<td><a href="<c:url value='/deletemessage/${messages.id}' />">Delete</a><span class="glyphicon glyphicon-trash"></span>
					<a href="<c:url value='/message?uid=${messages.fromuser}'/>">Reply</a><span class="glyphicon glyphicon-pencil"></span></td>
					<td><c:out value="${messages.subject}"></c:out></td>
					<td><c:out value="${messages.content}"></c:out></td>
					
					

				</tr>
			</c:forEach>

		</table>
	</div>