package kibbler

class PetController extends RestfulController<Pet> {

    PetController() {
        super( Pet )
    }

    @Override
    protected Pet queryForResource( Serializable id ) {
        def orgId = params.organizationId
        Pet.where{ organization.id == orgId }
    }
}
