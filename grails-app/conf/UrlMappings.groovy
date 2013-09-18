class UrlMappings {

	static mappings = {

        "/" ( controller: "dashboard", action: "index" )
        "/contract" ( controller: "dashboard", action: "contract" )

        "/login/$action?"( controller: "login" )

        "/upload/photo" {
            controller = "upload"
            action = "photo"
        }

        "/activate"( controller: 'user', action: 'activate' )

        "/user/switchTo"( controller: 'user', action: 'switchTo' )

        "/suggestions/$action"( controller: "suggestion" )

        "/pets/$id/$action" ( controller: 'pets' )
        "/people/$id/$action" ( controller: 'people' )
        "/organization/$id/transactions" {
            controller = "organization"
            action = [POST: "addTransaction", GET: "listTransactions"]
        }
        "/organization/$id/$action" ( controller: 'organization' )

        "/organization/$id/terms-text" {
            controller = "organization"
            action = "termsText"
        }

        "/organization/$orgId/documents/$action" {
            controller = 'documents'
        }

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

		"500"(view:'/error')
	}
}
