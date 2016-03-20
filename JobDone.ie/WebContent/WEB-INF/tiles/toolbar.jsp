<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:choose>
	<c:when test="${hasOffer}">
		<a href="${pageContext.request.contextPath}/createjobpost">Edit or delete your current offer.</a>
	</c:when>
	<c:otherwise>
		<p>
			<a href="${pageContext.request.contextPath}/createjobpost">Add a
				new offer.</a>
		</p>
</c:otherwise>

</c:choose>


&nbsp;
<sec:authorize access="hasRole('ROLE_ADMIN')">
	
		<a href="<c:url value='/admin'/>">Admin</a>
	
</sec:authorize>
&nbsp;
<sec:authorize access="isAuthenticated()">
	
		<a href="<c:url value='/messages'/>">Messages (<span id ="messageNumber">0</span>)</a>
	
</sec:authorize>
&nbsp;
 <h1>Search for Jobs</h1>
    <form action="doSearch" method="post">
      Search: <input type="text" name="searchText" /><br/>
      <input type="reset"/>
      <input type="submit"/>
    </form>
    

