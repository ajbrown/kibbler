class UrlMappings {

	static mappings = {

        "/" ( controller: "dashboard", action: "index" )
        "/login/$action?"( controller: "login" )

        "/organization/$id/transactions" {
            controller = "organization"
            action = [POST: "addTransaction", GET: "listTransactions"]
        }

        "/organization/$orgId/documents/$action" {
            controller = 'documents'
        }

        "/pdf/$action?"( controller: "pdf" )

        "/$controller/create"( action: "create" )
        "/$controller" ( action: "index" )
        "/$controller/index" ( action: "index" )

        "/$controller/$id" {
            action = [GET: "read", POST: "update", PUT: "create", DELETE: "delete"]
        }

        "/pets/$id/$action" ( controller: 'pets' )
        "/people/$id/$action" ( controller: 'people' )


		"500"(view:'/error')
	}
}
