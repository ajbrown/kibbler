<r:require module="font-awesome"/>

<div class="span3">
    <ul class="nav nav-list nav-stacked" id="people-list-nav">
        <li>
            <a href="#" data-bind="click: people.showCreate">
                <i class="icon-plus"></i> Add New
            </a>
        </li>
        <!-- ko foreach: people.list() -->
        <li data-bind="attr: { 'data-id': id() }, css: { active: $data.id() == $root.people.activeId() }">
            <a href="#"
               data-bind="attr: { href: '#people/' + id() }, text: name()"></a>
        </li>
        <!-- /ko -->
    </ul>
</div>
<section class="span9 main-section" id="person-info-pane" data-bind="with: people.active()">
    <form id="person-info-form" data-bind="attr: { action: url }">
        <div class="row">
            <div class="span9">
                <legend class="caps">

                    <div class="btn-group pull-right" style="margin-left: 25px;">
                        <a class="btn btn-mini"
                           data-bind="css: { 'btn-danger': doNotAdopt(), active: doNotAdopt() }, click: toggleDoNotAdopt">Do Not Adopt</a>
                    </div>

                    <div class="btn-group pull-right">
                        <button type="button"
                                class="btn btn-mini"
                                data-bind="css: { active: adopter() }, click: toggleAdopter, disable : doNotAdopt"
                                autocomplete="off">Adopter</button>
                        <button type="button"
                                class="btn btn-mini"
                                data-bind="css: { active: foster() }, click: toggleFoster, disable: doNotAdopt"
                                autocomplete="off">Foster</button>
                        <button type="button"
                                class="btn btn-mini"
                                data-bind="css: { active: teamMember() }, click: toggleTeamMember, disable: doNotAdopt"
                                autocomplete="off">Team Member</button>
                    </div>

                    <span class="editable"
                          data-bind="text: name, css: { 'warning' : doNotAdopt() }"></span>

                </legend>

            </div>
        </div>

        <div class="row">
            <div class="span4">
                <div>
                    <a href="#" id="person-edit-address-button" data-bind="click: $root.people.editAddress"
                       class="pull-right edit-button">Edit</a>
                    <strong>Contact Info</strong>
                </div>

                    <!-- Contact Info -->
                    <div data-bind="visible: company()" id="person-company-wrapper">
                        <i class="icon-building"></i>&nbsp;<span id="person-company" data-bind="text: company"></span>
                    </div>
                    <address class="" data-bind="html: addressFormatted"></address>
                    <div data-bind="visible: phone()" id="person-phone-wrapper">
                        <i class="icon-phone">&nbsp;</i>&nbsp;<span id="person-phone" data-bind="text: phone"></span>
                    </div>
                    <div data-bind="visible: email()" id="person-email-wrapper">
                            <i class="icon-envelope-alt">&nbsp;</i>&nbsp;<span id="person-email"
                                                                       data-bind="text: email"></span>
                    </div>
            </div>
            <div class="span5">
                <div class="editable-text">
                    <label for="person-notes">Notes <small>(Organization Visible)</small></label>
                    <textarea
                            id="person-notes"
                            name="notes"
                            style="height: 140px;"
                            class="stretch-width"
                            data-bind="value: notes, autosave: { event: 'keypress', field: 'notes' }"></textarea>

                </div>
            </div>
        </div>

        <div class="row">
            <div class="span4">
                <div>
                    <strong>Fostering <a href="#" class="small">(add)</a></strong>
                </div>
                <small data-bind="visible: !fostering() || fostering().length == 0"> None </small>

                <!-- ko foreach: fostering -->
                <div>
                    <a href="#"
                       data-bind="click: $parent.removeFoster"><i class="icon-remove"></i></a>&nbsp;
                    <span data-bind="text: givenName()"></span>
                </div>
                <!-- /ko -->

                <div>
                    <strong>Adopted <a href="#" class="small">(add)</a></strong>
                </div>
                <small data-bind="visible: !adopted() || adopted().length == 0"> None </small>

                <!-- ko foreach: adopted -->
                <div>
                    <a href="#"
                       data-bind="click: $parent.removeAdoption"><i class="icon-remove"></i></a>&nbsp;
                    <span data-bind="text: givenName()"></span>
                </div>
                <!-- /ko -->

            </div>
            <div class="span5">
                <h4>History and Documentation  <a href="#" class="small">(add)</a></h4>
                <table class="table-condensed">
                    <tr>
                        <th>Event</th>
                        <th>Add/Changed By</th>
                        <th>Date</th>
                        <th>Attachement</th>
                    </tr>
                </table>
            </div>
        </div>


        <!-- Edit Address Modal -->
        <div class="reveal-modal" id="person-edit-address-modal">
            <legend data-bind="text: 'Edit ' + name() + '\'s Contact Info'">Edit Contact Info</legend>

            <label>Company Name</label>
            <input type="text" name="company" data-bind="value: company, autosave: true"/>

            <label>Address</label>
            <textarea name="address" data-bind="value: address, autosave: true"></textarea>

            <label>Phone</label>
            <input type="text" name="phone" data-bind="value: phone, autosave: true"/>

            <label>E-mail</label>
            <input type="text" name="email" data-bind="value: email, autosave: true"/>

            <button type="button" class="btn">Done.</button>
        </div>

    </form>

</section>
