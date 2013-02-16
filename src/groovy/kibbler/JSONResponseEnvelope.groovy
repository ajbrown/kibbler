package kibbler

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 2/16/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
class JSONResponseEnvelope {
    Integer status
    Collection errors = []

    Object data
}
