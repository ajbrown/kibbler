<div class="row vertical-offset-100" >
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-body" ng-controller="ResetCtrl">
                <div class="" style="padding:0 0 10px;text-align: center;">
                    <img src="${g.resource( file: 'images/KibblerLogo.png')}" alt="" style="display: block; margin: 0 auto"/>
                    <h3>Let's Reset Your Password.</h3>
                    <div class="alert alert-{{resetAlert.type}} animated bounceIn"
                         ng-if="resetAlert.message">{{resetAlert.message}}</div>
                </div>
                <form accept-charset="UTF-8" role="form" name="recoverForm" novalidate>
                    <fieldset ng-show="!email || !confirmSent">

                        <p class="wizard-help">
                            Enter your e-mail address below, and we'll help you reset your password.  We'll verify that you
                            own the email address by sending you a confirmation code.
                        </p>

                        <div class="form-group">
                            <label>What's your email address?</label>
                            <input class="form-control" autofocus id="recoverEmail"
                                   ng-disabled="code && correctCode"
                                   ng-model="email" type="email" required>
                        </div>

                        <button class="btn btn-primary btn-block"
                                ng-show="!correctCode || !code"
                                ng-click="requestConfirmCode(email)"
                        >{{confirmSent ? 'Resend' : 'Send'}} Confirmation Code</button>
                    </fieldset>

                    <fieldset id="confirmCode" ng-show="confirmSent && !correctCode">
                        <p class="wizard-help">
                            Ok, We've sent an email to <strong>{{email}}</strong> with a confirmation code.  When you get it,
                            enter the code below.
                        </p>

                        <div class="form-group">
                            <label>What was your confirmation code?</label>
                            <input class="form-control confirm-code"
                                   ng-model="code" ng-bind="code" required>
                        </div>

                        <button class="btn lg btn-primary btn-block"
                                ng-click="checkConfirmCode(email, code)"
                        >Check Confirmation Code</button>

                    </fieldset>

                    <fieldset id="resetPasswordGroup" ng-show="correctCode">

                        <p class="wizard-help">
                            <span ng-if="user.name">Hi there, {{user.name}}! </span>Go ahead and choose a new password. Make it
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

                                ng-disabled="loginForm.$invalid"
                                ng-click="resetPassword(email,code,password)">Reset My Password</button>
                    </fieldset>


                </form>

                <div id="loginHelp">
                    <p ng-show="email && confirmSent">
                        Whoops, <a href="#" ng-click="resetState()">I entered the wrong email address</a>.
                    </p>
                    <p ng-show="confirmSent && email && !code">
                        Please <a href="#" ng-click="requestConfirmCode(email)">resend my code</a>.
                    </p>
                    <p>
                        Nevermind, <a ng-href="login">I know my password.</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>