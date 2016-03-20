<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	

<table class="offers" >
	<tr>
		<td>Title</td>
		<td>Domain</td>
		<td>Description</td>
	</tr>	
		<c:forEach var="jobpost" items="${search1}">
				<tr>
					
					<td><c:out value="${jobpost.getJobTitle()}"></c:out></td>
					<td><c:out value="${jobpost.getJobDomain()}"></c:out></td>
					<td><c:out value="${jobpost.getJobDescription()}"></c:out></td>
					
		
				</tr>
			</c:forEach>

</table>

    
   