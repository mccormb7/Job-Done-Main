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
		var doDelete = confirm("Are you sure you want to delete your profile?");
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

<script type="text/javascript">
function CheckColors(val){
 var element=document.getElementById('color');
 if(val=='pick a color'||val==' ')
   element.style.display='block';
 else  
   element.style.display='none';
}

</script> 



<!-- title, domain, description, photo, location. price -->





<!-- Top content -->
<div class="top-content">

	<div class="inner-bg">
		
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3 form-box">
					<div class="form-top">
						<div class="form-top-left">
							<h3>Edit your Profile</h3>
							<p>Add as much info as possible to maximize possible
								recommendations</p>
						</div>
						<div class="form-top-right">
							<i class="fa fa-lock"></i>
						</div>
					</div>



					<div class="form-bottom">


						<!-- ===================================form edit profile input========================================== -->
						<sf:form method="post"
							action="${pageContext.request.contextPath}/docreateprofile"
							commandName="provider">
							<sf:input type="hidden" name="id" path="id" />




							<div class="form-group">
							<b><font size="6">Title:</font></b>
								<label class="sr-only" for="form-title">Title..</label>
								<sf:input type="text" path="title" name="title"
									placeholder="Title" class="form-title form-control"
									id="form-title"></sf:input>
							</div>
							<div class="error">
								<sf:errors path="title"></sf:errors>
							</div>



							


							<div class="form-group">
							<b><font size="6">Experience:</font></b>
								<label class="sr-only" for="form-email">Experience:</label>
								<sf:textarea path="experience" type="text"
									name="experience" placeholder="Experience.."
									class="form-name form-control" id="form-email" rows="10"
									cols="10"></sf:textarea>
							</div>
							<div class="error">
								<sf:errors path="experience"></sf:errors>
							</div>


							<div class="form-group">
							<b><font size="6">Qualifications:</font></b>
								<label class="sr-only" for="form-email">Qualifications:</label>
								<sf:textarea path="qualifications" type="text"
									name="qualifications" placeholder="Qualifications.."
									class="form-name form-control" id="form-qualifications"
									rows="5" cols="5"></sf:textarea>
							</div>
							<div class="error">
								<sf:errors path="qualifications"></sf:errors>
							</div>


							<div class="form-group">
							<b><font size="6">Domain:</font></b>
								<label class="sr-only" for="form-role">Domain</label>
								<sf:select onchange='CheckColors(this.value);' type="text" path="domain" name="domain"
									placeholder="Email.." class="form-role form-control"
									id="form-role">
									<sf:option value="" label="Select Domain available" />
									<sf:options items="${unique}" path="domain" name="domain" />
									<sf:option value=" ">Other</sf:option>
								</sf:select>	
								<input type="text" name="domain" id="color" style='display:none;'/>
							</div>
							<div class="error">
								<sf:errors path="domain"></sf:errors>
							</div>





							<div class="form-group">
							<b><font size="6">Gender:</font></b>
								<label class="sr-only" for="form-role">Gender</label>
								<sf:select type="text" path="gender" name="gender"
									placeholder="Gender.." class="form-role form-control"
									id="form-role">
									<sf:option value="male">Male</sf:option>
									<sf:option value="female">Female</sf:option>

								</sf:select>
							</div>
							<div class="error">
								<sf:errors path="gender"></sf:errors>
							</div>


							<div class="form-group">
							<b><font size="6">Location:</font></b>
								<label class="sr-only" for="form-location">Location..</label>
								<sf:input type="text" path="location" name="location"
									id="searchTextField" placeholder="Name.."
									class="form-name form-control"></sf:input>
							</div>
							<div class="error">
								<sf:errors path="location"></sf:errors>
							</div>


							<div class="form-group">
							<b><font size="6">Price per Hour Est..</font><b/>
								<label class="sr-only" for="form-price">Price</label>
								<sf:input type="text" path="price" name="price"
									placeholder="price.." class="form-name form-control"
									id="form-name"></sf:input>
							</div>
							<div class="error">
								<sf:errors path="price"></sf:errors>
							</div>

						
							<div class="form-group">
							<b><font size="6">Profile Picture URL</font></b>
								<label class="sr-only" for="form-price">Enter Profile Picture URL</label>
								<sf:input type="text" path="internetpic" name="internetpic"
									placeholder="profile pic url.." class="form-name form-control"
									id="form-name"></sf:input>
							</div>
							<div class="error">
								<sf:errors path="internetpic"></sf:errors>
							</div>
							

							<button type="submit" value="Save Profile" class="btn">Save Profile</button>
							
							<c:if test="${tradesman.id != 0}">
							&nbsp;
							<button type="submit" name="delete" id="delete" value="Delete your profile" class="btnx">Delete your profile</button>
							
							
							</c:if>
						</sf:form>
					</div>
				</div>
			</div>

		</div>
	</div>

