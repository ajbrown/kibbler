<div class="row vertical-offset-100" >
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="" style="padding:0 0 10px;text-align: center;">
                    <img src="${g.resource( file: 'images/KibblerLogo.png')}" alt="" style="display: block; margin: 0 auto"/>
                    <h3>Let's Activate Your Account.</h3>

                    <div class="alert alert-{{alert.type}} animated bounceIn"
                         ng-if="alert.message">{{resetAlert.message}}</div>

                    <div class="alert alert-danger animated bounceIn"
                         ng-if="!verifyToken && tokenChecked">
                        The activation link you clicked is no longer valid.  If you continue to have problems
                        activating your account, please contact us by clicking the question mark icon to the bottom
                        right.
                    </div>

                </div>

                <form accept-charset="UTF-8" role="form" name="form" novalidate ng-hide="!verifyToken">

                    <fieldset id="resetPasswordGroup">

                        <div class="form-group">
                            <label>What's Your Name?</label>
                            <input class="form-control"
                                ng-model="name" required/>

                        </div>

                        <p class="wizard-help" ng-show="name">
                            <span ng-if="name">Hi there, {{name}}! </span>Go ahead and choose a new password. Make it
                        something hard to guess, but easy to remember.
                        </p>

                        <div class="form-group">
                            <label>Choose a new Password</label>
                            <input class="form-control" placeholder="Choose a new Password"
                                   ng-model="password" type="password" required>
                        </div>

                        <div class="form-group">
                            <label>Type it again to verify</label>
                            <input class="form-control" placeholder="Type your password again"
                                   ng-model="passwordConfirm" type="password" required>
                        </div>

                        <button class="btn btn-success btn-block"

                                ng-disabled="form.$invalid"
                                ng-click="activate(code,name,password)">Reset My Password</button>
                    </fieldset>

                </form>

                <div id="loginHelp">

                </div>
            </div>
        </div>
    </div>
</div>