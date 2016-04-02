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
              <h3 class="panel-title"> Username : ${jobpost.user.username}</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src="https://cdn3.iconfinder.com/data/icons/simplius-pack/512/user-512.png"> </div>
                
                
                <div class=" col-md-9 col-lg-9 "> 
                
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td>Title:</td>
                        <td>${jobpost.title}</td>
                      </tr>
                      <tr>
                        <td>Description</td>
                        <td>${jobpost.description}</td>
                      </tr>
                      <tr>
                        <td>Domain</td>
                        <td>${jobpost.domain}</td>
                      </tr>
                   
                        <tr>
                        <td>Address</td>
                        <td>${jobpost.location}</td>
                      </tr>
                      <tr>
                        <td>Email</td>
                        <td><a href = "<c:url value='/message?uid=${jobpost.user.username}'/>">contact</a></td>
                      </tr>
                     
                      

                     
                    </tbody>
                  </table>
                
                </div>
              </div>
            </div>
                 <div class="panel-footer">
                        <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary"><i class="glyphicon glyphicon-envelope"></i></a>
                        <span class="pull-right">
                            <a href="edit.html" data-original-title="Edit this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
                            <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger"><i class="glyphicon glyphicon-remove"></i></a>
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

	