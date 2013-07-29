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


    <a data-toggle="modal" href="#invite-user-modal" class="btn btn-primary btn-large">Invite a User</a>


    <!-- Invite User Modal -->
    <div class="modal fade" id="invite-user-modal">
        <g:form controller="usersAdmin" action="invite" method="post">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Invite a User</h4>
                </div>
            </div>
            <div class="modal-body">

                <div class="control-group">
                    <label>Name</label>
                    <input type="text" name="name" value="">
                </div>

                <div class="control-group">
                    <label>E-Mail Address</label>
                    <input type="text" name="email" value="">
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
        </div>

        </g:form>
    </div>
    </body>
</html>
