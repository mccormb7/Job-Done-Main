<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
  <script type="text/javascript">

	function onDeleteClick(event) {
		var doDelete = confirm("Are you sure youwant to delete this offer?");
		if (doDelete == false) {
			event.preventDefault();
		}
	}

	function onReady() {
		$("#delete").click(onDeleteClick);
	}
	$(document).ready(onReady);
	
</script>

  <div class="container">
   <div class="col-sm-9 col-md-10">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs">
                <li class="active"><a href="#home" data-toggle="tab"><span class="glyphicon glyphicon-inbox">
                </span>Primary</a></li>
                
                </span></a></li>
            </ul>
            
            <div class="tab-content">
                <div class="tab-pane fade in active" id="home">
                    <div class="list-group">
                        <a href="#" class="list-group-item">
                          
							<table class="table table-striped table-bordered table-condensed">
								
								<thead>
								<tr>
								
									<td>Name</td>
									<td>	</td>
									<td>	</td>
									<td> Subject </td>
									<td> 	</td>
									<td>Their Username</td>
								
								</tr>
								</thead>
								<tbody>
								
									<c:forEach var="messages" items="${messages1}">
										<tr>
								
											<td> <span class="glyphicon glyphicon-star-empty"><c:out value="${messages.name}"></c:out></span></td>
											<td ><span class="glyphicon glyphicon-trash"></span><a href="<c:url value='/deletemessage/${messages.id}' />" >Delete</a></td>
											<td><span class="glyphicon glyphicon-pencil"></span><a href = "<c:url value='/message?uid=${messages.fromuser}'/>">Reply</a></td>
											<td><c:out value="${messages.subject}"></c:out></td>
											<td><c:out value="${messages.fromuser}"></c:out></td>
											<td><c:out value="${messages.username}"></c:out></td>
								
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						
						</a></div> 
  
						  </div>

</div>
</div>




