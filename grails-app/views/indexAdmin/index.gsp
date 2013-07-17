<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 6/9/13
  Time: 1:37 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="kibbler.User; kibbler.Organization; kibbler.Person; kibbler.Pet" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
	</head>
	<body>
    <h1>Administration</h1>

    <ul>
        <li><g:link controller="organizationsAdmin">Organizations ( <%= Organization.count() %> )</g:link></li>
        <li><g:link controller="petsAdmin">Pets ( <%= Pet.count() %> )</g:link></li>
        <li><g:link controller="personsAdmin">People ( <%= Person.count() %> )</g:link></li>
        <li><g:link controller="usersAdmin">Users ( <%= User.count() %> )</g:link></li>
    </ul>
    </body>
</html>
