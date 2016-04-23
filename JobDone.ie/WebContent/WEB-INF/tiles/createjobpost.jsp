<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
	//var a = document.getElementById('searchTextField');
	var address = document.getElementById('searchTextField');

	geocoder.geocode({
		'address' : address
	}, function(results, status) {

		if (status == google.maps.GeocoderStatus.OK) {
			var latitude = results[0].geometry.location.lat();
			var longitude = results[0].geometry.location.lng();
			alert(latitude + ' ' + ' ' + longitude);

			function onCheckClick(event) {
				alert(latitude + '   ' + longitude);
			}

			function onReadytwo() {
				$("#submit").click(onCheckClick);
			}
			$(document).ready(onReadytwo);

		}
	});
</script>








<!-- Top content -->
<div class="top-content">

	<div class="inner-bg">

		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 form-box">
				<div class="form-top">
					<div class="form-top-left">
						<h3>Add a new Task Post</h3>
						<p>Give as much details as possible</p>
					</div>
					<div class="form-top-right">
						<i class="fa fa-lock"></i>
					</div>
				</div>



				<div class="form-bottom">


					<!-- ===================================form edit profile input========================================== -->
					<sf:form method="post"
						action="${pageContext.request.contextPath}/docreatejob"
						commandName="jobpost">

						<sf:input type="hidden" name="id" path="id" />



						<div class="form-group">
							<b><font size="6">Title:</font></b> <label class="sr-only"
								for="form-title">Title..</label>
							<sf:input type="text" path="title" name="title"
								placeholder="Title" class="form-title form-control"
								id="form-title"></sf:input>
						</div>
						<div class="error">
							<sf:errors path="title"></sf:errors>
						</div>


						<!-- New house with 20 separate window frames that need to be varnished and painted. -->



						<div class="form-group">
							<b><font size="6">Description:</font></b> <label class="sr-only"
								for="form-email">Description</label>
							<sf:textarea path="description" type="text" name="description"
								placeholder="Description.." class="form-name form-control"
								id="form-description" rows="10" cols="10"></sf:textarea>
						</div>
						<div class="error">
							<sf:errors path="description"></sf:errors>
						</div>




						<div class="form-group">
							<b><font size="6">Domain:</font></b> <label class="sr-only"
								for="form-role">Domain</label>
							<sf:select type="text" path="domain" name="domain"
								placeholder="Email.." class="form-role form-control"
								id="form-role">
								<sf:option value="grinds">Grinds</sf:option>
								<sf:option selected="selected" value="painter">Painter</sf:option>
								<sf:option value="catering">Catering</sf:option>
								<sf:option value="gardening">Gardening</sf:option>

							</sf:select>
						</div>
						<div class="error">
							<sf:errors path="domain"></sf:errors>
						</div>



						<div class="form-group">
							<b><font size="6">Location:</font></b> <label class="sr-only"
								for="form-location">Location..</label>
							<sf:input type="text" path="location" name="location"
								id="searchTextField" class="form-name form-control"></sf:input>
						</div>
						<div class="error">
							<sf:errors path="location"></sf:errors>
						</div>


						<div class="form-group">
							<b><font size="6">Profile Picture URL</font></b> <label
								class="sr-only" for="form-price">Enter Profile Picture
								URL</label>
							<sf:input type="text" path="internetpic" name="internetpic"
								placeholder="profile pic url.." class="form-name form-control"
								id="form-name"></sf:input>
						</div>
						<div class="error">
							<sf:errors path="internetpic"></sf:errors>
						</div>


						<div class="form-group">
							<b><font size="6">Estimated Price:</font></b> <label
								class="sr-only" for="form-price">Price</label>
							<sf:input type="text" path="price" name="price"
								placeholder="price.." class="form-name form-control"
								id="form-name"></sf:input>
						</div>
						<div class="error">
							<sf:errors path="price"></sf:errors>
						</div>




						<button type="submit" value="Save Advert" class="btn" id="submit"
							name="submit">Save Advert</button>



						<c:if test="${jobpost.id != 0}">
							&nbsp;
							<button type="submit" name="delete" id="delete"
								value="Delete this task" class="btnx">Delete this task</button>


						</c:if>
					</sf:form>
				</div>
			</div>
		</div>

	</div>
</div>
