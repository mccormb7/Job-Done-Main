<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<div class="container">
      <div class="row">
      <div class="col-md-5  toppad  pull-right col-md-offset-3 ">

       <br>
<p class=" text-info"></p>
      </div>
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
   
   
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">${provider.user.username}</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src=${provider.internetpic} class="img-circle img-responsive"> </div>
       
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="tableprofile table-user-information">
                    <tbody>
                      <tr>
                        <td>Title:</td>
                        <td>${provider.title}</td>
                      </tr>
                      <tr>
                        <td>Experience</td>
                        <td>${provider.experience}</td>
                      </tr>
                      <tr>
                        <td>Qualifications</td>
                        <td>${provider.qualifications}</td>
                      </tr>
                   
                         <tr>
                             <tr>
                        <td>Gender</td>
                        <td>${provider.gender}</td>
                      </tr>
                        <tr>
                        <td>Address</td>
                        <td>${provider.location}</td>
                      </tr>
                      <tr>
                        <td>Message</td>
                        <td><a href = "<c:url value='/message?uid=${provider.user.username}'/>">contact</a></td>
                      </tr>
                      <tr>
                        <td>Email Directly</td>
                        <td><a href = "<c:url value='/sendmail'/>">contact</a></td>
                      </tr>
                     
                      

                     
                    </tbody>
                  </table>
                  
                </div>
              </div>
            </div>
                 <div class="panel-footer">
                 		   
                        <a data-original-title="Send Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary"><i class="glyphicon glyphicon-envelope"></i></a>
                    
                    
                        <span class="pull-right">
                            <a href="${pageContext.request.contextPath}/createprofile" data-original-title="Edit Your Profile" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
                            <a href="${pageContext.request.contextPath}/createprofile" data-original-title="Delete Your Profile" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger"><i class="glyphicon glyphicon-remove"></i></a>
                        </span>
                        
                        
                    </div>
            
          </div>
        </div>
      </div>
    </div>
    
  <script type="text/javascript">  
  $(document).ready(function() {
    var panels = $('.user-infos');
    var panelsButton = $('.dropdown-user');
    panels.hide();

    //Click dropdown
    panelsButton.click(function() {
        //get data-for attribute
        var dataFor = $(this).attr('data-for');
        var idFor = $(dataFor);

        //current button
        var currentButton = $(this);
        idFor.slideToggle(400, function() {
            //Completed slidetoggle
            if(idFor.is(':visible'))
            {
                currentButton.html('<i class="glyphicon glyphicon-chevron-up text-muted"></i>');
            }
            else
            {
                currentButton.html('<i class="glyphicon glyphicon-chevron-down text-muted"></i>');
            }
        })
    });


    $('[data-toggle="tooltip"]').tooltip();

    $('button').click(function(e) {
        e.preventDefault();
        alert("This is a demo.\n :-)");
    });
});
    
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