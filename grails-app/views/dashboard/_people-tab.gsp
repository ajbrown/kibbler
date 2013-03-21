<div class="span3">
    <ul class="nav nav-list nav-stacked" id="people-list-nav">
        <li>
            <a href="#" data-bind="click: people.showCreate">
                <i class="icon-plus"></i> Add New
            </a>
        </li>
        <!-- ko foreach: people.list() -->
        <li data-bind="attr: { 'data-id': id() }, css: { active: $data == $root.people.active }">
            <a href="#"
               data-bind="attr: { href: '#people/' + id() }, text: name()"></a>
        </li>
        <!-- /ko -->
    </ul>
</div>
<section class="span9 main-section" id="person-info-pane" data-bind="with: people.active()">
    <form id="person-info-form" data-bind="attr: { action: $root.people.activeUrl }">
        <div class="row">
            <div class="span9">
                <legend class="caps">
                    <label class="pull-right checkbox">
                        <input type="checkbox" name="foster"> Foster
                    </label>
                    <label class="pull-right checkbox">
                        <input type="checkbox" name="foster"> Adopter
                    </label>
                    <label class="pull-right checkbox">
                        <input type="checkbox" name="available"> Available
                    </label>
                    <span class="editable"
                          data-bind="text: name"></span>

                </legend>

            </div>
        </div>

        <div class="row">
            <div class="span4">
                <div class="editable-text">
                    <a href="#" id="person-contact-edit" class="pull-right">Edit</a>
                    <address class="" data-bind="html: contact"></address>
                </div>
            </div>
            <div class="span5">
                <div class="editable-text">
                    <a href="#" class="pull-right">Edit</a>
                    <label for="person-notes">Notes</label>
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
                    <label class="checkbox pull-right">
                        <input type="checkbox" name="available" data-bind="checked: available, autosave: 'available'"/>
                        Available
                    </label>
                    <strong>Fosters & Adoptions <a href="#" class="small">(add)</a></strong>
                </div>
                <table class="table-condensed">
                    <tr>
                        <th>Name</th>
                        <th>Since</th>
                    </tr>
                </table>

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
    </form>
</section>
