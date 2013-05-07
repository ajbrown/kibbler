package kibbler

import grails.plugin.cache.Cacheable
import org.springframework.web.servlet.support.RequestContextUtils

import javax.servlet.http.HttpServletRequest

class EventService {

    def messageSource

    /**
     *
     * @param et
     * @param org
     * @param person
     * @param args
     * @return
     */
    def create( EventType et, Person subject, User actor, List args = null ) {
        def newArgs = [subject.id, subject.class, actor.id ] + args
        def event = new Event(
                organization: subject.organization,
                actor: actor,
                person: subject,
                args: newArgs,
                type: et
        )
        event.save( failOnError: true )
    }

    /**
     *
     * @param et
     * @param org
     * @param person
     * @param args
     * @return
     */
    def create( EventType et, Pet subject, User actor, List args = null ) {
        def newArgs = ([subject.id, subject.class, actor.id] + args)
        def event = new Event(
                organization: subject.organization,
                pet: subject,
                actor: actor,
                args: newArgs,
                type: et
        )
        event.save( failOnError: true )
    }

    /**
     *
     * @param et
     * @param person
     * @param pet
     * @param args
     * @return
     */
    def create( EventType et, Organization subject, User actor, List args = null  ) {
        def newArgs = [subject.id, subject.class, actor.id] + args
        def event = new Event(
                organization: subject,
                actor: actor,
                args: newArgs,
                type: et
        )
        event.save( failOnError: true )
    }

    /**
     * Translate an event's messageCode into a message.
     * @param event The event to be translated
     * @param request The current request
     * @return The translated message
     */
    def String translateMessage( Event event, HttpServletRequest request ) {
        translateMessage( event, RequestContextUtils.getLocale( request ) )
    }

    /**
     * Translate an event's messageCode into a message
     * @param event
     * @param locale
     * @return
     */
    @Cacheable('event-translations')
    def String translateMessage( Event event, Locale locale ) {
        messageSource.getMessage( event.type.code, event.args.toArray(), locale )
    }

    /**
     * List all events for the specified organization for the last $limitDays.  Results will be sorted with most
     * recent first.
     * @param org
     * @param limitDays defaults to 30.  Use -1 to return entire event history.
     * @return
     */
    def listByOrganization( Organization org, int limitDays = 30 ) {
        limitDays = limitDays ?: 30

        Event.createCriteria().list() {
            eq "organization", org
            if( limitDays >= 0 ) {
                gte "dateCreated", ((new Date().clearTime()) - limitDays)
            }
            order "dateCreated", "desc"
            cache true
        }
    }

    /**
     * List all activities for the specified person for the last $limitDays.  Results will be sorted with most recent
     * first.
     * @param person
     * @param limitDays
     * @return
     */
    def listByPerson( Person person, int limitDays = 30 ) {
        limitDays = limitDays ?: 30

        Event.createCriteria().list() {
            eq "person", person
            eq "hidden", false
            if( limitDays >= 0 ) {
                gte "dateCreated", ((new Date().clearTime()) - limitDays)
            }
            order "dateCreated", "desc"
            cache true
        }
    }

    /**
     * List all activities for the specified pet for the last $limitDays.  Results will be sorted with most recent
     * first.
     * @param pet
     * @param limitDays
     * @return
     */
    def listByPet( Pet pet, int limitDays = 30 ) {
        limitDays = limitDays ?: 30

        Event.createCriteria().list() {
            eq "pet", pet
            eq "hidden", false
            if( limitDays >= 0 ) {
                gte "dateCreated", ((new Date().clearTime()) - limitDays)
            }
            order "dateCreated", "desc"
            cache true
        }
    }
}
