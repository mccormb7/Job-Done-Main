 Allow user to delete account(all jobs and profiles would become
unsearhable active=false) Allow user change name allow user change email
if email is avaiable and send confirmation to the new emaill adress

change password
<style>
.modal-body .form-horizontal .col-sm-2, .modal-body .form-horizontal .col-sm-10
	{
	width: 100%
}

.modal-body .form-horizontal .control-label {
	text-align: left;
}

.modal-body .form-horizontal .col-sm-offset-2 {
	margin-left: 15px;
}
</style>
<div class="top-content">

	<div class="inner-bg">

		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 form-box">
				<div class="form-top">
					<div class="form-top-left"></div>
					<div class="form-top-right">
						<i class="fa fa-lock"></i>
					</div>
				</div>



				<div class="form-bottom">

					<!-- Button trigger modal -->
					<button class="btn btn-primary btn-lg" data-toggle="modal"
						data-target="#myModalHorizontal">Update Password</button>

					<!-- Modal -->
					<div class="modal fade" id="myModalHorizontal" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<!-- Modal Header -->
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">
										<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Modal title</h4>
								</div>

								<!-- Modal Body -->
								<div class="modal-body">

									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label class="col-sm-2 control-label" for="inputEmail3">Current
												Password</label>
											<div class="col-sm-10">
												<input type="password" class="form-control" id="inputEmail3"
													placeholder="Current Password" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="inputPassword3">New
												Password</label>
											<div class="col-sm-10">
												<input type="password" class="form-control"
													id="inputPassword3" placeholder="Password" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="inputPassword3">Confirm
												New Password</label>
											<div class="col-sm-10">
												<input type="password" class="form-control"
													id="inputPassword3" placeholder="Password" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10"></div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10"></div>
										</div>
									</form>


								</div>

								<!-- Modal Footer -->
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary">Save
										changes</button>
								</div>
							</div>
						</div>
					</div>



					<!-- Button trigger modal -->
					<button class="btn btn-primary btn-lg" data-toggle="modal"
						data-target="#myModalHorizontal">Update Email Address</button>

					<!-- Modal -->
					<div class="modal fade" id="myModalHorizontal" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<!-- Modal Header -->
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">
										<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Modal title</h4>
								</div>

								<!-- Modal Body -->
								<div class="modal-body">

									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label class="col-sm-2 control-label" for="inputEmail3">Current
												Email Address</label>
											<div class="col-sm-10">
												<input type="password" class="form-control" id="inputEmail3"
													placeholder="Current Email" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="inputPassword3">Requested Email Address</label>
											<div class="col-sm-10">
												<input type="password" class="form-control"
													id="inputPassword3" placeholder="New Email" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="inputPassword3">Confirm
												New Email Address</label>
											<div class="col-sm-10">
												<input type="password" class="form-control"
													id="inputPassword3" placeholder="New Email" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10"></div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10"></div>
										</div>
									</form>


								</div>

								<!-- Modal Footer -->
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary">Save
										changes</button>
								</div>
							</div>
						</div>
					</div>







					<div class="form-group">






						<div class="form-group"></div>
					</div>
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

	$('#sidebar').affix({
		offset : {
			top : $('header').height()
		}
	});
</script>