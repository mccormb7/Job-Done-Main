<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>








<c:choose>
	<c:when test="${hasPostedJob}">




		<table class="table table-hover">
			<thead>
				<tr>
					<td>Name</td>
					<td></td>
					<td></td>
					<td>Task</td>
				</tr>
			</thead>
			<c:forEach var="jobpost" items="${jobposts2}">
				<tr class="info">

					<td><c:out value="${jobpost.user.name}"></c:out></td>
					<td><a  class="btn btn-danger" href="<c:url value='/remove/${jobpost.id}' />">Delete</a></td>
					<td><a class="btn btn-warning" href="<c:url value='/editjobpost/${jobpost.id}' />">Edit</a></td>

					<td><c:out value="${jobpost.description}"></c:out></td>

				</tr>
			</c:forEach>
		</table>



	</c:when>
	<c:otherwise>
		<p>


			<div class="inner-bg">

			<div class="row">
			<div class="col-sm-6 col-sm-offset-3 form-box">
				<div class="form-top">
				<font size="6" color="white">No Task Posts Made</font>
					<div class="form-top-center">
					<a class="btn btn-primary btn-lg" role="button"
								href="${pageContext.request.contextPath}/createjobpost">Create Task Here</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="btn btn-primary btn-lg" role="button"
								href="${pageContext.request.contextPath}/FAQ"> Get Help Here</a>	
					</div>
					
				</div>
		</div>
		</div>
	</div>
		
		
		
	</c:otherwise>

</c:choose>






<p/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


<!-- goes to url and tries to download the data and pass it to the function -->
<script type="text/javascript">
	function messageCountLink(data) {
		$("#messageNumber").text(data.number);

	}
	function pageLoad() {
		pageUpdate();
		window.setInterval(pageUpdate, 5000);

	}
	function pageUpdate() {

		$.getJSON("<c:url value="/getmessages"/>", messageCountLink);

	}

	$(document).ready(pageLoad);
</script>
