<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	



    
   <div class="inner-bg">
		
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 form-box">
				<div class="form-top">
				<font size="6" color="white">No Searchs Found, Please try again.</font>
					<div class="form-top-center">
					<a class="btn btn-primary btn-lg" role="button" href="${pageContext.request.contextPath}/">Home</a>
					<a class="btn btn-primary btn-lg" role="button" href="${pageContext.request.contextPath}/jobposts">View Current Tasks</a>	
					</div>
					
				</div>
		</div>
		</div>
	</div>