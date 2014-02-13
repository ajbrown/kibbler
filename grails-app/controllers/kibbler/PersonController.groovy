package kibbler

class PersonController extends RestfulController<Person> {

    PersonController() {
        super( Person )
    }

    @Override
    protected Person queryForResource( Serializable id ) {
        def orgId = params.organizationId
        Person.where{ organization.id == orgId }
    }

}
