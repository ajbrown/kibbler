<div class="span3">
    <ul class="nav nav-list nav-stacked" id="pets-list-nav">
        <li>
            <a href="#" data-bind="click: pets.showCreateModal">
                <i class="icon-plus"></i> Add New
            </a>
        </li>
        <!-- ko foreach: pets.list() -->
        <li data-bind="attr: { 'data-id': id }, css: { active: id() == $root.pets.activeId() }">
            <a href="#"
               data-bind="
                    attr: { href: '#pets/' + id() }, text: givenName"></a>
        </li>
        <!-- /ko -->
    </ul>
</div>
<section class="span9 main-section" id="pet-info-pane" data-bind="with: pets.active()">
    <form id="status-info-form" data-bind="attr: { action: url }" method="post">


        <div class="row">
            <div class="span9">
                <legend class="caps">
                    <small class="pull-right" id="pet-status-label"
                            data-bind="html: statusLabel"></small>
                    <small class="pull-right" id="pet-type-label"
                           data-bind="text: typeLabel"></small>
                    <span class="editable"
                        data-bind="text: givenName"></span>

                </legend>

            </div>
        </div>
        <div class="row">
            <!-- Image and Description -->

            <div class="span4">

            <ul class="nav nav-pills">
                <li class="dropdown">
                    <a class="dropdown-toggle" id="change-status-menu-toggle"
                       role="button" data-toggle="dropdown" href="#">Change Status <i class="icon-reorder"></i></a>
                    <ul class="dropdown-menu">
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
                    </ul>
                </li>
            </ul>

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

        <div class="row">
            <div class="span4">

            </div>
            <div class="span5">
                <div class="editable-text">
                    <a href="#" class="pull-right"><span data-bind="text: historyDays">30</span>
                        days <i class="icon-calendar"></i></a>
                    <label>History</label>
                    <table class="table-condensed table-striped">
                        <tbody data-bind="foreach: history">
                            <tr>
                                <td data-bind="html: AppService.translateMsgReferences( message, true )"></td>
                                <td data-bind="text: jQuery.timeago( event.dateCreated )"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </form>

    <div id="edit-pet-breed" style="display: none;">
        <form class="form-horizontal">
            <select name="sex" data-bind="value: sex">
                <option value="female">Female</option>
                <option value="male">Male</option>
            </select>
            <select name="species" data-bind="value: type">
                <option value="DOG">Dog</option>
                <option value="CAT">Cat</option>
            </select>
        </form>
    </div>

    <div id="pet-adopt-modal" class="reveal-modal">
        <h3>Adopt</h3>
        <form class="form-horizontal" method="post"
              data-bind="attr: { action: url + '/adopt' }">
            <label>Adopter</label>
            <select name="adopter"
                    data-bind="options: $root.people.listAdopters(), optionsText: 'name', optionsValue: 'id'">
            </select>

            <label class="checkbox">
                Adopt With Contract
                <input type="checkbox" name="contract" data-bind="click: $root.orgs.displayContractTerms"/>
            </label>

            <div id="contract-info" style="display:none;">
                <h4>Contract Terms</h4>
                <textarea id="contract-terms" name="contract-terms"></textarea>

                <p class="small">
                    By typing your full name in the box below, you acknowledge that you agree to the terms of the
                    contract.
                </p>

                <label for="name">Print your name</label>
                <input type="text" name="name" id="name" class="name"/>
            </div>

            <hr/>

            <button type="submit" class="btn btn-primary" data-bind="click: $root.pets.submitAdopt">Submit</button>
        </form>
    </div>
    <div id="pet-foster-modal" class="reveal-modal">
        <form class="form-horizontal" method="post"
              data-bind="attr: { action: url + '/foster' }">
            <fieldset>
                <legend data-bind="text: 'Foster ' + name">Foster</legend>
                <label><g:message code="pet.fostermodal.fosterId.label" default="Choose a foster"/></label>
                <select name="fosterId"
                        data-bind="options: $root.people.listFosters(), optionsText: 'name', optionsValue: 'id'">
                </select>
                <span class="help-block"><g:message code="pet.fostermodal.fosterId.help"/></span>

                <hr/>

                <a class="pull-right btn btn-cancel" data-bind="click: $('#pet-foster-modal').trigger('reveal:close')">
                    <g:message code="pet.fostermodal.cancel.label" default="Cancel"/></a>
                <button type="submit" class="btn btn-primary" data-bind="click: $root.pets.submitFoster">
                    <g:message code="pet.fostermodal.submit.label" default="Apply"/>
            </button>

            </fieldset>
        </form>
    </div>
</section>

<r:require module="signaturePad"/>
<r:script>
    (function() {
        $('.sigPad').signaturePad();
    })();
</r:script>
