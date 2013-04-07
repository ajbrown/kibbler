class UrlMappings {

	static mappings = {

        "/login/$action?"( controller: "login" )

        "/organization/$id/transactions" {
            controller = "organization"
            action = [POST: "addTransaction", GET: "listTransactions"]
        }

        "/$controller/create"( action: "create" )
        "/$controller" ( action: "index" )
        "/$controller/index" ( action: "index" )

        "/$controller/$id" {
            action = [GET: "read", POST: "update", PUT: "create", DELETE: "delete"]
        }

        "/pets/$id/$action" ( controller: 'pets' )
        "/people/$id/$action" ( controller: 'people' )


		"/" ( controller: "dashboard", action: "index" )
		"500"(view:'/error')
	}
}
