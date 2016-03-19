
    <h2>Found Books</h2>
 
    <c:forEach var="jobpost" items="${foundJobs}">
        <ul>
          <li>${jobpost.getTitle()}</li>
          <li>${jobpost.getDomain()}</li>
          <li>${jobpost.getDescription()}</li>
        </ul>
        <hr>
    </c:forEach>
