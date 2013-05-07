package kibbler

import org.bson.types.ObjectId

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 5/5/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EventSubject {
    ObjectId getId()
    Organization getOrganization()
}