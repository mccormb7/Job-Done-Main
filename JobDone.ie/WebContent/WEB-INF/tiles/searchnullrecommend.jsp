<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	



    
<sec:authorize access="hasRole('ROLE_TRADE')">
<h1>No Recommendations within the desired distance. Please try again with a different number</h1>
<form action="journey" method="post">
	Search: <input type="text" name="journeyString" /><br /> 
	<input type="reset" /> <input type="submit" />
</form>
</sec:authorize>  