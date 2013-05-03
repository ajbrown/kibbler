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
    def create( EventType et, Person subject, User actor, Object[] args = null ) {
        def event = new Event(
                organization: subject.organization,
                actor: actor,
                subject: subject,
                args: args,
                type: et
        )
        event.save()
    }

    /**
     *
     * @param et
     * @param org
     * @param person
     * @param args
     * @return
     */
    def create( EventType et, Pet subject, User actor, Object[] args = null ) {
        def event = new Event(
                organization: subject.organization,
                subject: subject,
                actor: actor,
                args: args,
                type: et
        )
        event.save()
    }

    /**
     *
     * @param et
     * @param person
     * @param pet
     * @param args
     * @return
     */
    def create( EventType et, Organization subject, User actor, Object[] args = null  ) {
        def event = new Event(
                organization: subject,
                subject: subject,
                actor: actor,
                args: args,
                type: et
        )
        event.save()
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
        def args = [ event.subject, event.actor ]
        args.addAll event.args
        messageSource.getMessage( event.type.code, args.toArray(), locale )
    }

    /**
     * List all events for the specified organization for the last $limitDays.  Results will be sorted with most
     * recent first.
     * @param org
     * @param limitDays defaults to 30.  Use -1 to return entire event history.
     * @return
     */
    def listByOrganization( Organization org, int limitDays = 30 ) {
        Event.createCriteria().list() {
            eq "organization", org
            if( limitDays >= 0 ) {
                lte "dateCreated", ((new Date().clearTime()) - limitDays)
            }
            order "dateCreated", "desc"
            readOnly true
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
        Event.createCriteria().list() {
            eq "person", person
            if( limitDays >= 0 ) {
                lte "dateCreated", ((new Date().clearTime()) - limitDays)
            }
            order "dateCreated", "desc"
            readOnly true
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
        Event.createCriteria().list() {
            eq "pet", pet
            if( limitDays >= 0 ) {
                lte "dateCreated", ((new Date().clearTime()) - limitDays)
            }
            order "dateCreated", "desc"
            readOnly true
            cache true
        }
    }
}
