<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	

<table id="table12" cellpadding="0" cellspacing="0" >


	<tr class ="danger">
		<th>Title</th>
		<th>Profile</th>
		<th>Domain</th>
		
		<th>Location</th>
	</tr>	
	<tbody>
		<c:forEach var="provider" items="${searchprovider}">
				<tr>
					
					<td><c:out value="${provider.getProviderTitle()}"></c:out></td>
					<td><a class="btn btn-success" href="<c:url value='/viewprofile/${provider.getProviderId()}' />" >View</a></td>
					
					<td><c:out value="${provider.getProviderDomain()}"></c:out></td>
					
					<td><c:out value="${provider.getProviderLocation()}"></c:out></td>
					
		
				</tr>
			</c:forEach>
	</tbody>
</table>

    
    
    
<script language="javascript" type="text/javascript">  
//<![CDATA[  
    var table12_Props = {  
        highlight_keywords: true,  
        on_keyup: true,  
        on_keyup_delay: 1500,  
        single_search_filter: true,  
        selectable: true  
    };  
    var tf12 = setFilterGrid( "table12",table12_Props );  
//]]>  
</script> 
   