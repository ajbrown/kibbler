<!-- HERE HERE HERE HERER-->

<div class="row vertical-offset-100">

    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default panel-3d">
            <div class="panel-body" ng-controller="LoginCtrl">
                <div class="" style="padding:0 0 10px;text-align: center;">
                    <img src="${g.resource( file: 'images/KibblerLogo.png')}" alt="" style="display: block; margin: 0 auto"/>
                    <h3>Please Login.</h3>
                </div>
                <div class="alert alert-danger" ng-show="loginFailure">{{loginFailure}}</div>
                <form accept-charset="UTF-8" role="form" name="loginForm" novalidate>
                    <fieldset>
                        <div class="form-group">
                            <input class="form-control" placeholder="E-mail"
                                   id="loginEmail"
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

                <div id="loginHelp">
                    Help me, <a ng-href="reset-password">I forgot my password.</a>
                </div>
            </div>
        </div>
    </div>
</div>