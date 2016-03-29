<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<table class="offers">
	<tr>
		<td>Name</td>
		<td>Email</td>
		<td>Offer</td>
	</tr>

	<c:forEach var="jobpost" items="${jobposts1}">
		<tr>

			<td><c:out value="${jobpost.user.name}"></c:out></td>

			<td><a href = "<c:url value='/message?uid=${jobpost.user.username}'/>">contact</a></td>

			<td><c:out value="${jobpost.description}"></c:out></td>

		</tr>
	</c:forEach>
</table>

<p/>

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
