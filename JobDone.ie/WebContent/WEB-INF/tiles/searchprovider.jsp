<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	

<table class="offers" >
<tr>
<td>List of Users that match your search</td></tr>
	<tr>
		<td>Title</td>
		<td>Profile</td>
		<td>Domain</td>
		
		<td>Location</td>
	</tr>	
		<c:forEach var="provider" items="${searchprovider}">
				<tr>
					
					<td><c:out value="${provider.getProviderTitle()}"></c:out></td>
					<td><a href="<c:url value='/viewprofile/${provider.getProviderId()}' />" >View</a></td>
					
					<td><c:out value="${provider.getProviderDomain()}"></c:out></td>
					
					<td><c:out value="${provider.getProviderLocation()}"></c:out></td>
					
		
				</tr>
			</c:forEach>

</table>

    
   