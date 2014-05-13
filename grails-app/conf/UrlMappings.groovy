class UrlMappings {

	static mappings = {


        "/reset-password"( controller: "user", action: "reset")

        "/login/authfail"( controller: "login", action: "authfail" )
        "/login/authAjax"( controller: "login", action: "authAjax" )
        "/login/ajaxSuccess"( controller: "login", action: "ajaxSuccess" )

        group( "/api/v1" ) {

            "/organizations"( resources: 'organization') {
                "/pets"( resources: 'pet' )
                "/people"( resources: 'person' )
            }

            "/users"( resources: 'user' ) {
                "/organizations"( resources: 'organization' )
            }
        }

        "/contract" ( controller: "dashboard", action: "contract" )


        "/upload/photo" {
            controller = "upload"
            action = "photo"
        }

        "/ng/external/$ngview" ( controller: 'ng', action: 'kibblerExternal')


        "/pages/$action/$slug" {
            controller = "pages"
        }

        "/pdf/$action?"( controller: "pdf" )


        "/admin" ( controller: "indexAdmin" )
        "/admin/users/$action?"( controller: "usersAdmin")
        "/admin/pets/$action?"( controller: "petsAdmin")
        "/admin/persons/$action?"( controller: "personsAdmin")
        "/admin/organizations/$action?"( controller: "organizationsAdmin")

        "/_view/$ngview" ( controller: "public", action: "view" )

        "/**"( view: 'index' )

		"500"(view:'/error')
	}
}
