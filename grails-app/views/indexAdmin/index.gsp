<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 6/9/13
  Time: 1:37 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
	</head>
	<body>
    <h1>Administration</h1>

    <ul>
        <li><g:link controller="organizationsAdmin">Organizations</g:link></li>
        <li><g:link controller="petsAdmin">Pets</g:link></li>
        <li><g:link controller="personsAdmin">People</g:link></li>
        <li><g:link controller="usersAdmin">Users</g:link></li>
    </ul>
    </body>
</html>
