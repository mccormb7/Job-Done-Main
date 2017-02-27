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


<!--  <script type="text/javascript">
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#search').keyup(function() {
				searchTable($(this).val());
			});
		});
		function searchTable(inputVal) {
			var table = $('#tblData');
			table.find('tr').each(function(index, row) {
				var allCells = $(row).find('td');
				if (allCells.length > 0) {
					var found = false;
					allCells.each(function(index, td) {
						var regExp = new RegExp(inputVal, 'i');
						if (regExp.test($(td).text())) {
							found = true;
							return false;
						}
					});
					if (found == true)
						$(row).show();
					else
						$(row).hide();
				}
			});
		}
	</script>

	


	<div class="CSSTableGenerator">
		<p>
			<label for="search"><strong><p style="color: #ffffff; background-color: #000000"; font-size: larger; >Enter keyword to search message inbox</p>
			</strong></label><input type="text" id="search" /><label></label>
		</p>
		<table id="tblData" class="target" width = "100%">

			<tr>
				<td>From</td>
				<td>Actions</td>
				<td>Subject</td>
				
				<td>Content</td>
			</tr>


			<c:forEach var="messages" items="${messages1}">
				<tr>
					<td>&nbsp<span class="glyphicon glyphicon-star-empty"><c:out
								value="${messages.fromuser}"></c:out></span></td>
					<td>&nbsp<a class="btn btn-danger" href="<c:url value='/deletemessage/${messages.id}' />">Delete</a>&nbsp<span class="glyphicon glyphicon-trash"></span>
					&nbsp &nbsp &nbsp &nbsp &nbsp <a class="btn btn-success href="<c:url value='/message?uid=${messages.fromuser}'/>">Reply</a>&nbsp<span class="glyphicon glyphicon-pencil"></span></td>
					<td><c:out value="${messages.subject}"></c:out></td>
					<td><c:out value="${messages.content}"></c:out></td>
					
					

				</tr>
			</c:forEach>

		</table>
	</div>
</div>	

-->
<p><font size="8" color="white">Message Inbox</font></p>
<table id="table12" cellpadding="0" cellspacing="0">
    <tr>
        <th>From</th>
        <th>Actions</th>
        <th>Subject</th>
        <th>Content</th>

    </tr>
   	<c:forEach var="messages" items="${messages1}">
				<tr>
					<td>&nbsp<span class="glyphicon glyphicon-star-empty"><c:out
								value="${messages.fromuser}"></c:out></span></td>
					<td>&nbsp<a class="btn btn-danger" href="<c:url value='/deletemessage/${messages.id}' />">Delete</a>&nbsp<span class="glyphicon glyphicon-trash"></span>
					<a class="btn btn-success" href="<c:url value='/message?uid=${messages.fromuser}'/>">Reply</a>&nbsp<span class="glyphicon glyphicon-pencil"></span></td>
						
					<td><c:out value="${messages.subject}"></c:out></td>
					<td><c:out value="${messages.content}"></c:out></td>
					
					

				</tr>
			</c:forEach>
</table>

<script>
//<![CDATA[ 
var table12_Props = {
    highlight_keywords: true,
    on_keyup: true,
    on_keyup_delay: 1500,
    single_search_filter: true,
    selectable: true
};

var tf12 = setFilterGrid("table12", table12_Props);
//]]>  
</script>  
	
