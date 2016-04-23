<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	
	

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
	

	google.maps.event.addDomListener(window, 'load', init);

	function onDeleteClick(event) {

		var doDelete = confirm("Are you sure you want to delete this offer?");

		if (doDelete == false) {
			event.preventDefault();
		}
	}

	function onReady() {
		$("#delete").click(onDeleteClick);
	}

	$(document).ready(onReady);
//-->
</script>

<sf:form method="post"
	action="${pageContext.request.contextPath}/docreate"
	commandName="offer">
	<sf:input type="hidden" name="id" path="id" />

	<table class="formtable">

		<tr>
			<td>Title: <input type="text" name="firstname" value="Mickey">
				<br> Last name: <input type="text" name="lastname"
				value="Mouse">

			</td>
		</tr>
		<tr>
			<td><label for="locationTextField">Location</label> <input
				id="locationTextField" type="text" size="50">
			<td>
			<input id="searchTextField" type="text" size="50">
		</tr>
		<tr>
			<td>Domain of Job<select>
					<option value="Tutor">Tutor</option>
					<option value="Carpender">Carpender</option>
					<option value="Builder">Builder</option>
					<option value="Gardener">Gardener</option>
			</select></td>
		</tr>
		<tr>


			<input type="submit" value="Submit">
			<td class="label">Your offer:</td>
			<td><sf:textarea class="control" path="text" name="text"
					rows="10" cols="10"></sf:textarea><br /> <sf:errors path="text"
					cssClass="error"></sf:errors></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" value="Save advert" type="submit" /></td>
		</tr>

		<c:if test="${offer.id != 0}">
			<tr>
				<td class="label"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="delete control" name="delete" id="delete"
					value="Delete this offer." type="submit" /></td>
			</tr>
		</c:if>
	</table>

</sf:form>
