<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasRole('ROLE_ADMIN')">

<h1>Admin Dashboard</h1>

<table id="demo">
    <thead>
        <tr>
            <th>Name</th>
            <th>Username</th>
            <th>Message</th>
            <th>Authority</th>
            <th>Enabled</th>
            <th>Email</th>

        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${users}">

			<tr>
			<td><c:out value="${user.name}"></c:out></td>
			<td><c:out value="${user.username}"></c:out></td>
			<td><a class="btn btn-primary" href = "<c:url value='/message?uid=${user.username}'/>">contact</a></td>
        	<td><c:out value="${user.authority}"></c:out></td>
        	<!--  <td><c:out value="${user.enabled}"></c:out></td>-->
 
			<c:if test="${user.enabled == 'true' }">
        	<td><a  class="btn btn-danger" href="<c:url value='/unEnabledAccount/${user.username}' />">UnEnable</a></td>
        	</c:if>
			<c:if test="${user.enabled == 'false' }">
			<td><a  class="btn btn-success" href="<c:url value='/unEnabledAccount/${user.username}' />">Enable</a></td>
			</c:if>
			
        	<td><c:out value="${user.email}"></c:out></td>
        	</tr>
        </c:forEach>
 </tbody>
 </table>

</sec:authorize>

<script src="//assets.codepen.io/assets/common/stopExecutionOnTimeout-ddaa1eeb67d762ab8aad46508017908c.js"></script>
 <script src='https://koalyptus.github.io/TableFilter/tablefilter/tablefilter.js'></script>
<script src='https://koalyptus.github.io/TableFilter/tablefilter/tf-1.js'></script>


        <script>
      var filtersConfig = {
    base_path: '',
    col_2: 'select',
    col_3: 'select',
    col_4: 'select',
    alternate_rows: true,
    rows_counter: true,
    btn_reset: true,
    loader: true,
    mark_active_columns: true,
    highlight_keywords: true,
    col_number_format: [
        null,
        null,
        'US',
        'US',
        'US',
        'US',
        'US',
        'US',
        'US'
    ],
   
    col_widths: [
        '150px',
        '100px',
        '100px',
        '70px',
        '70px',
        '70px',
        '70px',
        '60px',
        '60px'
    ],
    extensions: [{
            name: 'sort',
            images_path: 'https://koalyptus.github.io/TableFilter/tablefilter/style/themes/'
        }]
};
var tf = new TableFilter('demo', filtersConfig);
tf.init();
      //# sourceURL=pen.js
    </script>
    <script>
  if (document.location.search.match(/type=embed/gi)) {
    window.parent.postMessage("resize", "*");
  }
</script>