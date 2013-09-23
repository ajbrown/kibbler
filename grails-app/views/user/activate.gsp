<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 9/7/13
  Time: 1:58 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
  <meta name="layout" content="main">
</head>
<body>

<div class="container">

    <div class="well">

        <h1>Activate Your Account</h1>

        <p class="lead">Your account is nearly ready. We need to ask you a few questions to finish setting up your account.</p>

        <g:form method="post" controller="user" action="activate" params="[code:cmd.code]" class="form">

            <div class="control-group">
                <label for="name" class="">What's your name?</label>
                <input type="text" id="name" name="name" class="input-xlarge" required/>
            </div>

            <div class="control-group">
                <label>Choose a password</label>
                <input type="password" name="password" id="password" class="input-xlarge" required/>
            </div>

            <div class="control-group">
                <button class="btn btn-primary btn-large">Create My Account</button>
            </div>

        </g:form>

    </div>
</div>

</body>
</html>