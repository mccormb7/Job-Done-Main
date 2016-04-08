<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2>Send Message</h2>

User: ${fromUser}
<sf:form method="post" commandName="message">

	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}" />
	<input type="hidden" name="_eventId" value="send" />
	
	

	<table class="formtable">
			
			<!-- fromName created in flowScope -->
			<!-- Auto attaches details from user when message is being sent -->
			<sf:input class="control" path="name" type="hidden"  value = "${fromName }"/>
			<sf:input class="control" path="fromuser" type="hidden" value = "${fromUsername}" />
				
			
		<tr>
			<td class="label">Subject:</td>
			<td><sf:input class="control" path="subject" type="text" /><br />
				<div class="error">
					<sf:errors path="subject"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label">Your Message:</td>
			<td><sf:textarea class="control" path="content" name="name"
					type="text" /><br />
				<div class="error">
					<sf:errors path="content"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" value="Send message" type="submit" /></td>
		</tr>
	</table>

</sf:form>
