
	
    <h1>Search for Jobs</h1>
    <form action="doSearch" method="post">
      Search: <input type="text" name="searchText" /><br/>
      <input type="reset"/>
      <input type="submit"/>
    </form>
    
    
    <c:forEach var="jobpost" items="${foundPosts}">
        <ul>
          <li>${jobpost.getDescription()}</li>
          
        </ul>
        <hr>
    </c:forEach>