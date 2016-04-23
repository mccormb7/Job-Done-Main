<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>






<div class="top-content">

	<div class="inner-bg">

		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 form-box">
				<div class="form-top">
					<div class="form-top-left">
						
					</div>
					<div class="form-top-right">
						<i class="fa fa-lock"></i>
					</div>
				</div>



				<div class="form-bottom">


					<!-- ===================================form edit profile input========================================== -->
					<sf:form method="post" commandName="message">

						<input type="hidden" name="_flowExecutionKey"
							value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="send" />

						<sf:input class="control" path="name" type="hidden"
							value="${fromName }" />
						<sf:input class="control" path="fromuser" type="hidden"
							value="${fromUsername}" />





						<div class="form-group">
							<b><font size="6">Subject:</font></b> <label class="sr-only"
								for="form-title">Subject..</label>
							<sf:input type="text" path="subject" 
								placeholder="Subject" class="form-title form-control"
								id="form-subject"></sf:input>
						</div>
						<div class="error">
							<sf:errors path="subject"></sf:errors>
						</div>






						<div class="form-group">
							<b><font size="6">Your Message:</font></b> <label class="sr-only"
								for="form-email">Your Message:</label>
							<sf:textarea path="content" name="content" type="text" 
								placeholder="Content.." class="form-name form-control"
								id="form-content" rows="10" cols="10"></sf:textarea>
						</div>
						<div class="error">
							<sf:errors path="content"></sf:errors>
						</div>


					



						<button type="submit" value="Send message" class="btn">Send Message</button>

						
					</sf:form>
				</div>
			</div>
		</div>

	</div>
</div>






















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

