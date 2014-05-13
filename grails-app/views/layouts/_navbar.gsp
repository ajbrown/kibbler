<!-- Fixed navbar -->
<div ng-show="$storage.user"
     class="navbar navbar-inverse navbar-fixed-top animated fadeInDownBig navbar-raised"
     role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Kibbler</a>

        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown" ng-if="$storage.user">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                         <img class="img-circle" alt="" style="margin: 0px 4px;"
                             src="http://www.gravatar.com/avatar/{{ $storage.user.email | gravatar }}?s=30"/>

                         {{$storage.user.name}} <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a ng-href="/profile">My Profile</a></li>
                        <li class="divider"></li>
                        <li><a ng-click="logout()"><i class="glyphicon glyphicon-off"> </i> Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>