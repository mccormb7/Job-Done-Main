Mail was Sent, Return <a href="${pageContext.request.contextPath}/">Home</a>


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
