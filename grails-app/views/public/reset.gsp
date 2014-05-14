<div class="row vertical-offset-100" >
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-body">
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


                </form>

                <div id="loginHelp">
                    <p ng-show="email && confirmSent">
                        Whoops, <a ng-click="resetState()">I entered the wrong email address</a>.
                    </p>
                    <p ng-show="email && confirmSent">
                        Please <a ng-click="requestConfirmCode(email)">resend my code</a>.
                    </p>
                    <p>
                        Nevermind, <a href="sign-in">I know my password.</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>