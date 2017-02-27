<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
.navbar-brand {
  transform: translateX(-50%);
  left: 50%;
  position: absolute;
}


/* DEMO example styles for logo image */
.navbar-brand {
  padding: 0px;
}
.navbar-brand>img {
  height: 100%;
  width: auto;
  padding: 7px 14px;
}

/* Fixed navbar */
body {
    padding-top: 90px;
}
/* General sizing */
ul.dropdown-lr {
  width: 300px;
}

/* mobile fix */
@media (max-width: 768px) {
	.dropdown-lr h3 {
		color: #eee;
	}
	.dropdown-lr label {
		color: #eee;
	}
}

</style>


<div class="masthead">
	<nav class="navbar transparent navbar-inverse navbar-fixed-top"
		height="500px" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-inner">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

		</div>


		<!-- allows Provides to search for specific jobs---done -->


		<div class="col-sm-3 col-md-3">
			<!-- what the trade person will search for when type into the box -->
			<sec:authorize access="hasRole('ROLE_TRADE')">
				<form class="navbar-form" role="search" action="doSearch"
					method="post">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search For Tasks" name="searchText">

						<div class="input-group-btn">
							<button class="btn btn-default" type="submit">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</form>
			</sec:authorize>


			<!-- what the client will see when they search -->
			<sec:authorize access="hasRole('ROLE_USER')">
				<form class="navbar-form" role="search" action="doSearchProvider"
					method="post">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search For Providers" name="searchTextPro">

						<div class="input-group-btn">
							<button class="btn btn-default" type="submit">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</form>
				
			</sec:authorize>
		</div>
		
		<div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        

              <a class="navbar-brand" ><span style="font-family:Cursive;font-size:40px;font-style:italic;font-weight:bold;text-transform:capitalize;font-variant:small-caps;color:white;background-color:222;" href="<c:url value='/'/>">Task Tackler</span>
           
      </a>
      </div>


		<div class="navbar-header navbar-right">
			
		</div>

		<sec:authorize access="isAuthenticated()">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<c:url value='/messageinbox'/>"><span
						class="glyphicon glyphicon-envelope  fa-2x"></span> (<span class="badge"
						id="messageNumber">0</span>)</a></li>
			
		</sec:authorize>
		

		<ul class="nav navbar-nav navbar-right">
			<sec:authorize access="!isAuthenticated()">
				<li id="FAQ"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a class="login" href="<c:url value='/FAQ'/>">FAQ's</a></li>
			</sec:authorize>
			
			<sec:authorize access="!isAuthenticated()">
				<li id="Register"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a class="login" href="<c:url value='/newaccount'/>">Register</a></li>
			</sec:authorize>
			
		
			<sec:authorize access="!isAuthenticated()">
				<li id="Login"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a   class="login" href="<c:url value='/login'/>">Login</a></li>
			
			</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><span
						class="glyphicon glyphicon-user fa-2x"></span> <b class="caret"></b></a>
						<ul class="dropdown-menu" id="mb1" class="menubar root-level" role="menubar" title="Styling Menu" aria-controls="st1">
						<li id="log-out"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/j_spring_security_logout'/>">Log
								out</a></li>
							<li id="Admin"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/admin'/>">Admin Panel</a></li>
							<li class="divider"></li>
						<li></li>
					</ul></li>
			</sec:authorize>
			<li><a></a></li>



			<sec:authorize access="hasRole('ROLE_TRADE')">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><span
						class="glyphicon glyphicon-user fa-2x"></span> <b class="caret"></b></a>

					<ul class="dropdown-menu" id="mb1" class="menubar root-level" role="menubar" title="Styling Menu" aria-controls="st1">
						<li id="log-out"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/j_spring_security_logout'/>">Log
								out</a></li>
						<li id="settings"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/settings'/>">settings</a></li>
						<li id="FAQ"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/FAQ'/>">FAQ</a></li>
						<li id="View-your-profile"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/profile'/>">View your profile</a></li>
						<li id="Edit-Profile"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/createprofile'/>">Edit Profile</a></li>
						<li id="Home"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/'/>">View all Job Posts</a></li>
								<li id="View-your-recommened-jobs"
						        class="menu-item checked"
						        role="menuitemradio"
						        tabindex="-1"
						        aria-controls="st1"
						        aria-checked="true"><a href="<c:url value='/recommendationpro'/>">View your Recommended Jobs</a></li>
						
								<li id="Update-profile-to-get-job-recommendations"
							        class="menu-item checked"
							        role="menuitemradio"
							        tabindex="-1"
							        aria-controls="st1"
							        aria-checked="true"><a href="<c:url value='/createprofile'/>">Update profile to get job Recommendations</a></li>
						        
							
						<li class="divider"></li>
						<li></li>
					</ul></li>
			</sec:authorize>
			
			<sec:authorize access="hasRole('ROLE_USER')">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><span
						class="glyphicon glyphicon-user fa-2x"></span> <b class="caret"></b></a>

					<ul class="dropdown-menu">
						<li id="log-out"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/j_spring_security_logout'/>">Log
								out</a></li>
						<li id="settings"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/settings'/>">Settings</a></li>
						<li id="View Posted Tasks"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/jobposts'/>">View Posted Tasks</a></li>
						<li id="Create-New-Task"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/createjobpost'/>">Create New Task</a></li>
						<li id="FAQ"
					        class="menu-item checked"
					        role="menuitemradio"
					        tabindex="-1"
					        aria-controls="st1"
					        aria-checked="true"><a href="<c:url value='/FAQ'/>">FAQ</a></li>
				        <li id="Home"
						        class="menu-item checked"
						        role="menuitemradio"
						        tabindex="-1"
						        aria-controls="st1"
						        aria-checked="true"><a href="<c:url value='/'/>">View Providers</a></li>
						<li class="divider"></li>
						<li></li>
					</ul></li>
			</sec:authorize>
			<li><a></a></li>

			
		</ul>
		<ul>
			<li></li>
		</ul>
</div>


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

<!-- /.navbar-collapse -

	


<sec:authorize access="hasRole('ROLE_TRADE')">

				<!-- user cant be recommended jobs unless the profile is filled out -->
		<!--  	<c:choose>
					<c:when test="${hasProfile}">

						<a class="navbar-brand" href="#">Recommended Tasks<span
							class="glyphicon glyphicon-cloud-upload  fa-3x"></span>
						</a>


					</c:when>
					<c:otherwise>
						<a class="navbar-brand" href="#">Update profile for
							Recommendations<span
							class="glyphicon glyphicon-cloud-upload  fa-3x"></span>
						</a>


					</c:otherwise>

				</c:choose>
			</sec:authorize>-->	




<!-- 
 
<a class="title" href="<c:url value='/'/>">TaskTackler.ie</a>


<sec:authorize access="isAuthenticated()">
<a href="${pageContext.request.contextPath}/settings">Settings</a>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
<a class="login" href="<c:url value='/login'/>">Log in</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
<a class="login" href="<c:url value='/j_spring_security_logout'/>">Log out</a>
</sec:authorize>


 -->
