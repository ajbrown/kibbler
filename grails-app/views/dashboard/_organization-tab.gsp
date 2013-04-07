<div class="span3">
    <ul class="nav nav-list nav-stacked" id="org-docs-list-nav">
        <li>
            <a href="#" data-bind="">
                <i class="icon-plus"></i> Add New
            </a>
        </li>
    </ul>
</div>

<section class="span9 main-section" id="organization-info-pane" data-bind="with: orgs.active()">
    <form id="person-info-form" data-bind="">
        <div class="row">
            <div class="span4">
                <label>Organization Info</label>
                <p></p>
            </div>
            <div class="span5">
                <a href="#" class="pull-right">30 days</a>
                <label>Organization History</label>
                <p>History</p>
            </div>
        </div>

        <div class="row">
            <div class="span4">
                <a href="#" data-bind="click: $root.orgs.newEntry" class="pull-right">New Entry</a>
                <label>Finance</label>

                <table id="org-transactions-table" class="table table-striped table-condensed table-hover">
                    <thead>
                        <tr>
                            <th>Amount</th>
                            <th>Event</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody data-bind="foreach: transactions">
                        <tr>
                            <td data-field="amount"
                                data-bind="text: (amountCents / 100).toMoney(), css: { moneyNegative: amountCents < 0 }"></td>
                            <td data-field="category" data-bind="text: category"></td>
                            <td data-field="date"     data-bind="text: prettyDate( dateCreated )"></td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </div>
    </form>
</section>

<div class="reveal-modal" id="modal-org-add-finance" data-bind="with: orgs.newEntryData">
    <form method="post">
        <h3>Add New Transaction</h3>

        <label>Type</label>
        <select name="type"
                data-bind="value: selected, options: $root.orgs.financeCategories, optionsText: 'name', optionsCaption: 'Choose One...'">
        </select>

        <div class="input-prepend">
            <label>Amount</label>
            <span class="add-on">
                <i class="icon-plus"  data-bind="visible: !selected() || selected().type == '+'"></i>
                <i class="icon-minus" data-bind="visible: selected() && selected().type == '-'"></i>
            </span>
            <input type="text" name="amount" data-bind="value: amount" value=""/>
        </div>

        <div data-bind="visible: selected() && selected().scope == 'pet'">
            <label>For</label>
            <select name="for"
                    data-bind="value: pet, enable: selected() && selected().scope == 'pet',
                            options: $root.pets.list(), optionsCaption: 'Choose...', value: 'id', optionsText: 'givenName'">
            </select>
        </div>

        <button type="submit" class="btn btn-primary"
                data-bind="enable: selected(), click: $root.orgs.submitNewEntry">Submit</button>
    </form>
</div>