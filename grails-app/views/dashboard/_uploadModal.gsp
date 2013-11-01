<!-- Upload Modal -->
<div class="reveal-modal" id="upload-modal">

    <form class="form" action="" method="post">
    <g:form controller="pets" action="upload">

    <legend>Add a Document</legend>

    <div class="alert" style="display:none;"></div>

    <label>Select a file</label>
    <input type="file" name="file"/>

    <label>Display Name</label>
    <input type="name" name="name" data-bind="">

    <hr/>

    <button type="button" class="btn btn-primary" data-bind="">Finished</button>

    </g:form>
</div>
