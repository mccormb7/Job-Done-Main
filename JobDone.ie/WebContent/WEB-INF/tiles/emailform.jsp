<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


    
    
    
 
<!-- Top content -->
<div class="top-content">

	<div class="inner-bg">
		
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3 form-box">
					<div class="form-top">
						<div class="form-top-left">
							<h3>Send Email to user</h3>
							<p>Note the email the user receives will be sent from the Task Tackler main email address and your details will be attached to the email.</p>
						</div>
						<div class="form-top-right">
							<i class="fa fa-lock"></i>
						</div>
					</div>



					<div class="form-bottom">

					<!-- ===================================form edit profile input========================================== -->
			
					
					<form method="post" action="sendmail">

						<input type="hidden" name="recipient" size="65" id="test" />



						<div class="form-group">
							<b><font size="6">Subject:</font></b> <label class="sr-only"
								for="form-title">Subject..</label>
							<input type="text" path="subject" 
								placeholder="Subject" class="form-title form-control"
								id="form-subject"></input>
						</div>
						






						<div class="form-group">
							<b><font size="6">Your Message:</font></b> <label class="sr-only"
								for="form-email">Your Message:</label>
							<textarea path="message" name="message" type="text" 
								placeholder="Message.." class="form-name form-control"
								id="form-content" rows="10" cols="10"></textarea>
						</div>
						


					    



						<button type="submit" value="Send E-mail" class="btn">Send Email</button>

						
					</form>
				</div>
			</div>
		</div>

	</div>
</div>
    
    
    
    
    
    
 
<!-- gets the email addres the email has to be sent to -->
<script type="text/javascript">
	
	function getParameterByName(name, url) {
	    if (!url) url = window.location.href;
	    name = name.replace(/[\[\]]/g, "\\$&");
	    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)", "i"),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, " "));
	}
	
</script>
<script>
var foo = getParameterByName('uid');

document.getElementById("test").value = foo;

</script>

<!-- goes to url and tries to download the data and pass it to the function -->
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
 