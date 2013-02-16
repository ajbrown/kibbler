<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Sign in &middot; Kibbler</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <r:require modules="bootstrap,styling"/>

    <style type="text/css">
    body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
    }

    .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        box-shadow: 0 1px 2px rgba(0,0,0,.05);
    }
    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;

    }
    .form-signin input[type="text"],
    .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
    }

    </style>

    <r:layoutResources />
</head>

<body class="simple">

<div class="container">

    <form class="form-signin" action="${postUrl}" method="post" id="loginForm">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="input-block-level"
               name="j_username"
               value="aj@synklabs.com" <% //TODO: remove hardcoded username %>
               placeholder="<g:message code="springSecurity.login.username.label"/>">
        <input type="password" class="input-block-level"
               name="j_password"
               value="123456" <% //TODO: remove hardcoded password %>
               placeholder="<g:message code="springSecurity.login.password.label"/>">
        <label class="checkbox">
            <input type="checkbox" name="${rememberMeParameter}" value="remember-me" <g:if test='${hasCookie}'>checked='checked'</g:if>>
            <g:message code="springSecurity.login.remember.me.label"/>
        </label>
        <button class="btn btn-large btn-primary" type="submit">${message(code: "springSecurity.login.button")}</button>
    </form>

</div> <!-- /container -->

<r:layoutResources/>

<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>

