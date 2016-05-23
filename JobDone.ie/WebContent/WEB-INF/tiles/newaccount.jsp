<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>







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
                        			<h3>Sign Up</h3>
                            		<p>Enter your details below to sign up :</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-lock"></i>
                        		</div>
                            </div>
                            
      
                            
                            <div class="form-bottom">
                            

                            
			                    <sf:form role="form" id="details" action="${pageContext.request.contextPath}/createaccount" method="post" commandName="user" class="login-form">
			                    
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Username..</label>
			                        	<sf:input type="text"  path="username" name="username" placeholder="Username" class="form-username form-control" id="form-username"></sf:input>
			                        </div>
			                        <div class="error">
										<sf:errors path="username"></sf:errors>
									</div>
														
									
									
									<div class="form-group">
			                    		<label class="sr-only" for="form-name">Name..</label>
			                        	<sf:input type="text" path="name" name="name" placeholder="Name.." class="form-name form-control" id="form-name"></sf:input>
			                        </div>
			                        <div class="error">
										<sf:errors path="name"></sf:errors>
									</div>
									
									
			
									
									<div class="form-group">
			                    		<label class="sr-only" for="form-email">Email</label>
			                        	<sf:input type="text" path="email" name="email" placeholder="Email.." class="form-name form-control" id="form-email"></sf:input>
			                        </div>
			                        <div class="error">
										<sf:errors path="email"></sf:errors>
									</div>
									
	
									<div class="form-group">
			                    		<label class="sr-only" for="form-role">Role</label>
			                        	<sf:select type="text" path="authority" name="authority" placeholder="Email.." class="form-role form-control" id="form-role">
				                        	<sf:option value="ROLE_TRADE">Provider</sf:option>
											<sf:option selected="selected" value="ROLE_USER">Client</sf:option>
											
										 </sf:select>
			                        </div>
			                        <div class="error">
										<sf:errors path="authority"></sf:errors>
									</div>
									
									
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<sf:input path="password" name="password" type="password" placeholder="Password.." class="form-password form-control" id="password"></sf:input>
			                        </div>
			                        <div class="error">
										<sf:errors path="password"></sf:errors>
									</div>
	

									
									<div class="form-group">
			                        	<label class="sr-only" for="form-cpassword">Confirm Password</label>
			                        	<input type="password" name="confirmpass" placeholder="Confirm Password.." class="form-cpassword form-control" id="confirmpass"></input>
			                        </div>
			                        <div id="matchpass"></div>
									
				
			                        <button type="submit"  value="Create account" class="btn">Submit!</button>
			                    </sf:form>
		                    </div>
                        </div>
                    </div>
                 
                </div>
            </div>
            
        </div>
