package kibbler

import grails.plugin.restfulCacheHeaders.CacheableRestfulController

/**
 * Created by ajbrown on 2/12/14.
 */
abstract class RestfulController<T> extends CacheableRestfulController<T> {

    static responseFormats = ['json']

    RestfulController( Class<T> resource ) {
        super( resource )
    }


    @Override
    protected List<T> listAllResources( Map params ) {
        params.max = params.max ?: 100
        params.offset = params.offset ?: (params.page ? (params.page-1 * params.max) : 0 )

        resource.createCriteria().list( max: params.max ?: 100, offset: params.offset ?: 0 ) {
            parametersToBind.findAll{ it.value }.each{
                eq it.key, it.value
            }

            order "name"
        }
    }

    /**
     * Allow any domain properties to be bound, and do type conversion.
     * @return
     */
    @Override
    protected Map getParametersToBind() {
        def obj = resource.create()
        bindData( obj, params.findAll{ it.key != 'id' } )

        def props = obj.properties.keySet()
        params.findAll{ it.key in props }.collectEntries{ [it.key, obj[it.key] ] }
    }
}
