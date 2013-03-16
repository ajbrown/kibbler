package kibbler

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 2/4/13
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
enum Species {
    DOG('Dog'), CAT('Cat')

    String label

    Species( String label ) {
        this.label = label
    }

}
