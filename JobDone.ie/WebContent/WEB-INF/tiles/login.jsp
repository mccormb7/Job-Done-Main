<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function() {
		document.f.j_username.focus();
	});
</script>

 


<c:if test="${param.error != null}">

	<p class="error">Login failed. Check that your username and
		password are correct.</p>

</c:if>

<!--  
<form name='f'
	action='${pageContext.request.contextPath}/j_spring_security_check'
	method='POST'>
	<table class="formtable">
		<tr>
			<td>User:</td>
			<td><input type='text' name='j_username' value=''></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='j_password' /></td>
		</tr>
		<tr>
			<td>Remember me:</td>
			<td><input type='checkbox' name='_spring_security_remember_me'
				checked="checked" /></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit" value="login" /></td>
		</tr>
	</table>
</form>
-->



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
                        
                        <p>

	

</p>
                    </div>
                 
                </div>
            </div>
            
        </div>


