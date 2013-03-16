<div class="span3">
    <ul class="nav nav-list nav-stacked" id="pets-list-nav">
        <li>
            <a href="#" data-bind="click: pets.showCreateModal">
                <i class="icon-plus"></i> Add New
            </a>
        </li>
        <!-- ko foreach: pets.list() -->
        <li data-bind="attr: { 'data-id': id }">
            <a href="#"
               data-bind="attr: { href: '#pets/' + id() }, text: givenName"></a>
        </li>
        <!-- /ko -->
    </ul>
</div>
<section class="span9 main-section" id="pet-info-pane" data-bind="with: pets.active()">
    <form id="status-info-form" data-bind="attr: { action: $root.pets.activeUrl }" method="post">

        <div class="row">
            <div class="span9">
                <legend class="caps">
                    <small class="pull-right"
                           data-popover="edit-pet-breed"
                           data-bind="text: typeLabel"></small>
                    <span class="editable"
                        data-bind="text: givenName"></span>

                </legend>

            </div>
        </div>
        <div class="row">
            <!-- Image and Description -->

            <div class="span4">
                <div id="pet-status-line">
                    <select name="status"
                            data-bind="value: status, options: ['${kibbler.Pet.STATUS_OPTIONS.join("','")}'], autosave: true"
                            id="pet-status">
                    </select>
                    <small data-bind="text: statusExtra"></small>
                </div>
                <div id="pet-photo-container">
                    <img
                            style="width: 200px;"
                            src="http://distilleryimage8.s3.amazonaws.com/2f2dccbe8ad611e2b59422000a9f13f8_6.jpg" alt=""/>
                </div>
            </div>

            <div class="span5">
                <div class="editable-text">
                    <a href="#" class="pull-right">Edit</a>
                    <label for="pet-description">Description</label>
                    <textarea
                            id="pet-description"
                            name="description"
                            style="height: 160px;"
                            class="stretch-width"
                            data-bind="value: description, autosave: { event: 'keypress', field: 'description' }"></textarea>

                </div>
            </div>
        </div>


        <div class="row">
            <div class="span4">

                <fieldset>

                    <label class="checkbox">
                        <input type="checkbox" name="heartworm"
                               data-bind="checked: heartworm, autosave: { field: 'heartworm' }">
                        Heartworm
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="housebroken"
                               data-bind="checked: housebroken, autosave: { field: 'housebroken' }">
                        Housebroken
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="microchipped"
                               data-bind="checked: microchipped, autosave: { field: 'microchipped' }">
                        Microchipped
                    </label>
                    <label data-bind="visible: microchipped()">
                        Microchip Id
                        <input type="text" name="microchipId" data-bind="value: microchipId, autosave: true"
                    class="input-small"/>
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="neutered"
                               data-bind="checked: neutered, autosave: { field: 'neutered' }">
                        <span data-bind="text: $root.pets.active().sex() == 'male' ? 'Neutered' : 'Spayed'">Spayed/Neutered</span>
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" name="specialNeeds"
                               data-bind="checked: specialNeeds, autosave: { field: 'specialNeeds' }">
                        Special Needs
                    </label>



                </fieldset>
            </div>
            <div class="span5">
                <div class="editable-text">
                    <a href="#" class="pull-right">Edit</a>
                    <label for="pet-notes">Notes (Not Publicly Viewable)</label>
                    <textarea
                            id="pet-notes"
                            name="notes"
                            style="height: 160px;"
                            class="stretch-width"
                            data-bind="value: notes, autosave: { event: 'keypress', field: 'notes' }"></textarea>

                </div>
            </div>
        </div>

    </form>
</section>


<div id="edit-pet-breed" style="display: none;">
    <form class="form-horizontal">
        <select name="sex" data-bind="value: pets.active.sex">
            <option value="female">Female</option>
            <option value="male">Male</option>
        </select>
        <select name="species" data-bind="value: pets.active.species">
            <option value="DOG">Dog</option>
            <option value="CAT">Cat</option>
        </select>
    </form>
</div>
