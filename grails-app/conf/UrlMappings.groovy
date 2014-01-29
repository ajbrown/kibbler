class UrlMappings {

	static mappings = {

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

        "/$controller/create"( action: "create" )
        "/$controller" ( action: "index" )
        "/$controller/index" ( action: "index" )


        "/$controller/$id" {
            action = [GET: "read", POST: "update", PUT: "create", DELETE: "delete"]
        }

        "/admin" ( controller: "indexAdmin" )
        "/admin/users/$action?"( controller: "usersAdmin")
        "/admin/pets/$action?"( controller: "petsAdmin")
        "/admin/persons/$action?"( controller: "personsAdmin")
        "/admin/organizations/$action?"( controller: "organizationsAdmin")

        "/_view/$ngview" ( controller: "public", action: "view" )
        "/**" ( controller: "public" )

		"500"(view:'/error')
	}
}
