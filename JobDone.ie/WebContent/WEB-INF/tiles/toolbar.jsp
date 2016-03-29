<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<sec:authorize access="hasRole('ROLE_USER')">
<c:choose>
	<c:when test="${hasJobPost}">
		<a href="${pageContext.request.contextPath}/createjobpost">Edit or
			delete your current offer.  ${user.username}</a>
	</c:when>
	<c:otherwise>
		<p>
			<a href="${pageContext.request.contextPath}/createjobpost">Add a
				new offer.  ${user.username}</a>
		</p>
	</c:otherwise>

</c:choose>
</sec:authorize>

&nbsp;
<sec:authorize access="hasRole('ROLE_ADMIN')">

	<a href="<c:url value='/admin'/>">Admin</a>

</sec:authorize>
&nbsp;
<sec:authorize access="isAuthenticated()">

	<a href="<c:url value='/messages'/>">Messages (<span
		id="messageNumber">0</span>)
	</a>

</sec:authorize>
&nbsp;
<sec:authorize access="hasRole('ROLE_TRADE')">
	<a href="<c:url value='/profile'/>">View your profile  ${user.username}</a>
</sec:authorize>
&nbsp;


<!-- allows Provides to search for specific jobs---done -->
<sec:authorize access="hasRole('ROLE_TRADE')">
<h1>Search for Jobs</h1>
<form action="doSearch" method="post">
	Search: <input type="text" name="searchText" /><br /> <input
		type="reset" /> <input type="submit" />
</form>
</sec:authorize>

<!-- allows users search for specfic Providers -->
<sec:authorize access="hasRole('ROLE_USER')">
<h1>Search for the right people for your job</h1>
<form action="doSearchProvider" method="post">
	Search: <input type="text" name="searchTextPro" /><br /> <input
		type="reset" /> <input type="submit" />
</form>
</sec:authorize>