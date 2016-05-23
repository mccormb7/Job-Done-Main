<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="isAuthenticated()">
	<!--  

	<h1> List of Job Posts </h1>
	<table class="table table-hover" id ="table6" >
		
		<thead >
			<tr class="danger" font-size= "7px">
			
				<td>Name:</td>
				<td>Details:</td>
				<td></td>
			
				<td>Title:</td>	
			</tr>
		</thead>
	
		<c:forEach var="jobpost" items="${jobposts1}">
			<tr class="info">
				<td><c:out value="${jobpost.user.name}"></c:out></td>
	
				<td><a class="btn btn-success" href="<c:url value='/viewjobpost/${jobpost.id}' />" >View </a></td>
		
				<td><a  class="btn btn-primary" href = "<c:url value='/message?uid=${jobpost.user.username}'/>">contact</a></td>
	
				
				<td><c:out value="${jobpost.title}"></c:out></td>
		
			</tr>
		</c:forEach>
	</table>
	
	<p/>
	
	<p>
	-->
	<h1> List of providers </h1>
	<table class="table table-hover">
		<tr>
			<td></td>
			<td>Name</td>
			<td>   </td>
			<td>Details</td>
			<td>Description</td>
			
		</tr>
	
		<c:forEach var="provider" items="${providers1}">
			<tr>
				<td><img style= "display:block; " width="100%" height="100%" src="${provider.internetpic}" /></td>
				<td><c:out value="${provider.user.name}"></c:out></td>
				<td><a class="btn btn-success" href="<c:url value='/viewprofile/${provider.id}' />" >View Profile</a></td>
				<td><a class="btn btn-primary" href = "<c:url value='/message?uid=${provider.user.username}'/>">contact</a></td>
		
				<td><c:out value="${provider.experience}"></c:out></td>
	
			</tr>
		</c:forEach>
	</table>

</sec:authorize>

<sec:authorize access="!isAuthenticated()">
<!-- Top content -->

        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong>Task Tackler</strong> 
							<br>
							For the task you need tackling </h1>
                           
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>Login</h3>
                            		<p>Enter your username and password to log on:</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-lock"></i>
                        		</div>
                            </div>
                            
                            <div class="form-bottom">
			                    <form role="form" action='${pageContext.request.contextPath}/j_spring_security_check' method='POST' class="login-form" name ='f'>
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Username</label>
			                        	<input type='text' name='j_username' placeholder="Username..." class="form-username form-control" id="form-username">
			                        </div>
			                        
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type='password' name='j_password' placeholder="Password..." class="form-password form-control" id="form-password">
			                        </div>
			                        <tr>
										<td>Remember me:</td>
										<td><input type='checkbox' name='_spring_security_remember_me'
											checked="checked" /></td>
									</tr>
			                        <button name="submit" type="submit" value="login" class="btn">Sign in</button>
			                        <a href="<c:url value="/newaccount" />">Create new account</a>
			                    </form>
		                    </div>
                        </div>

                    </div>
                 
                </div>
            </div>
            
        </div>

</sec:authorize>

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

<script>
//<![CDATA[  
  var table6_Props =  {  
                  paging: true,  
                  paging_length: 10,  
                  rows_counter: true,  
                  rows_counter_text: "Rows:",  
                  btn_reset: true,  
                  loader: true,  
                  loader_text: "Filtering data..."  
              };  
  var tf6 = setFilterGrid( "table6",table6_Props );  
//]]>  
</script>  

