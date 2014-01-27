<!DOCTYPE html>
<html lang="en" ng-app="kibblerExternal">
<head>
    <meta charset="utf-8">
    <title>Sign in &middot; Kibbler</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <r:script disposition="head">
    window.basePath = '${g.createLink( uri: '')}';
    </r:script>

    <r:require modules="login"/>

    <r:layoutResources />
</head>

<body>

<div class="container">
    <div class="row vertical-offset-100">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-body" id="loginPanel" ng-controller="LoginCtrl">
                    <div class="alert alert-danger" ng-show="loginFailure">{{loginFailure}}</div>
                    <form accept-charset="UTF-8" role="form" name="loginForm" novalidate>
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="E-mail"
                                       kb-focus="loginFailure" autofocus
                                       ng-model="user.email" name="j_username" type="email" required>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password"
                                       ng-model="user.password" name="j_password" type="password" required>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input ng-model="user.rememberMe" type="checkbox" value="Remember Me" checked="${rememberMe ?: false}"> Remember Me
                                </label>
                            </div>
                            <button class="btn btn-lg btn-success btn-block"
                                    ng-disabled="loginForm.$invalid"
                                    ng-click="login(user)">Login</button>
                        </fieldset>
                    </form>
                    <div class="row">
                        <a href="${g.createLink( controller: 'user', action: 'reset')}">I forgot my password.</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<r:require module="angularjs"/>

<r:layoutResources/>
</body>
</html>

