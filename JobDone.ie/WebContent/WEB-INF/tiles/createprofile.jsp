<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
	function initialize() {

		var input = document.getElementById('searchTextField');
		var autocomplete = new google.maps.places.Autocomplete(input);
	}

	google.maps.event.addDomListener(window, 'load', initialize);

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

	var geocoder = new google.maps.Geocoder();
	var address = "new york";

	geocoder.geocode({
		'address' : address
	}, function(results, status) {

		if (status == google.maps.GeocoderStatus.OK) {
			var latitude = results[0].geometry.location.lat();
			var longitude = results[0].geometry.location.lng();

		}
	});
</script>



<!-- title, domain, description, photo, location. price -->

<sf:form method="post"
	action="${pageContext.request.contextPath}/docreateprofile"
	commandName="provider">
	<sf:input type="hidden" name="id" path="id" />

	<table class="formtable">

		<tr>
			<td class="label">Title:</td>
			<td><sf:input class="control" path="title" name="title"
					type="text" /><br />
				<div class="error">
					<sf:errors path="title"></sf:errors>
				</div></td>
		</tr>
		
		<tr>
			<td class="label">Gender:</td>
			<td><sf:select path="gender" name="gender">
					<sf:option value="male">Male</sf:option>
					<sf:option value="female">Female</sf:option>
				</sf:select> <br />
				<div class="error">
					<sf:errors path="gender"></sf:errors>
		</tr>
		<tr>
			<td class="label">Domain:</td>
			<td><sf:select path="domain" name="domain">
					<sf:option value="grinds">Grinds</sf:option>
					<sf:option selected="selected" value="painter">Painter</sf:option>
					<sf:option value="catering">Catering</sf:option>
					<sf:option value="gardening">Gardening</sf:option>
				</sf:select> <br />
				<div class="error">
					<sf:errors path="domain"></sf:errors>
		</tr>



		<tr>
			<td class="label">Experience:</td>
			<td><sf:textarea class="control" path="experience" type="text"
					rows="10" cols="10"></sf:textarea><br /> <sf:errors
					path="experience" cssClass="error"></sf:errors></td>
		</tr>

		<tr>
			<td class="label">Qualifications:</td>
			<td><sf:textarea class="control" path="qualifications"
					type="text" rows="5" cols="5"></sf:textarea><br /> <sf:errors
					path="qualifications" cssClass="error"></sf:errors></td>
		</tr>


		<tr>
			<td class="label">Location:</td>
			<td><sf:input class="control" path="location" name="location"
					id="searchTextField" type="text" /><br />
				<div class="error">
					<sf:errors path="location"></sf:errors>
				</div></td>
		</tr>

		<tr>
			<td class="label">Price per hour</td>
			<td><sf:input class="control" path="price" name="price"
					type="text" /><br />
				<div class="error">
					<sf:errors path="price"></sf:errors>
				</div></td>
		</tr>



		<tr>
			<td class="label"></td>
			<td><input class="control" value="Save Profile" type="submit" /></td>
		</tr>

		<c:if test="${tradesman.id != 0}">
			<tr>
				<td class="label"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="delete control" name="delete" id="delete"
					value="Delete your profile" type="submit" /></td>
			</tr>
		</c:if>
	</table>

</sf:form>
