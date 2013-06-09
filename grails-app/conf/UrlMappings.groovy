class UrlMappings {

	static mappings = {

        "/" ( controller: "dashboard", action: "index" )
        "/login/$action?"( controller: "login" )


        "/suggestions/$action"( controller: "suggestion" )

        "/organization/$id/transactions" {
            controller = "organization"
            action = [POST: "addTransaction", GET: "listTransactions"]
        }

        "/organization/$id/terms-text" {
            controller = "organization"
            action = "termsText"
        }

        "/organization/$orgId/documents/$action" {
            controller = 'documents'
        }

        "/pages/$orgSlug/$petSlug" {
            controller = "pages"
            action = "pet"
        }

        "/pdf/$action?"( controller: "pdf" )

        "/$controller/create"( action: "create" )
        "/$controller" ( action: "index" )
        "/$controller/index" ( action: "index" )

        "/$controller/$id" {
            action = [GET: "read", POST: "update", PUT: "create", DELETE: "delete"]
        }

        "/pets/$id/$action" ( controller: 'petsAdmin' )
        "/people/$id/$action" ( controller: 'people' )

        "/upload/photo" {
            controller = "upload"
            action = "photo"
        }

        "/admin/users/$action?"( controller: "usersAdmin")
        "/admin/pets/$action?"( controller: "petsAdmin")
        "/admin/persons/$action?"( controller: "personsAdmin")
        "/admin/organizations/$action?"( controller: "organizationsAdmin")

		"500"(view:'/error')
	}
}
