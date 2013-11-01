<ul class="nav nav-pills">
    <li class="dropdown">
        <a class="" id="change-status-menu-toggle"
           role="button" data-toggle="dropdown" href="#">Actions <i class="icon-reorder"></i></a>
        <ul class="dropdown-menu">
            <li role="presentation">
                <a role="menuitem" href="#" data-bind="click: $root.pets.showPhotoUpload">Upload Photo</a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="#" data-bind="click: $root.pets.showDocUpload">Upload Document</a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="#"
                   data-bind="text: status() != 'adopted' ? 'Adopt' : 'Re-Adopt', click: $root.pets.adopt"
                >Adopt</a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="#"
                   data-bind="text: status() != 'fostered' ? 'Foster' : 'Re-Foster', click: $root.pets.foster"
                >Foster</a>
            </li>
            <li role="presentation" data-bind="visible: status() != 'available'">
                <a role="menuitem"
                   data-bind="visible: status() != 'available', click: $root.pets.reclaim"
                   href="#">Reclaim</a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="#" data-bind="click: $root.pets.hold">Hold</a>
            </li>
            <li class="divider"></li>
            <li role="presentation">
                <a role="menuitem" href="#"
                   data-bind="attr: { href: publicUrl }">
                    View Public Page
                </a>
            </li>
        </ul>
    </li>
</ul>
