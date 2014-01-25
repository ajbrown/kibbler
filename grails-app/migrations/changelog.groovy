databaseChangeLog = {

	changeSet(author: "ajbrown (generated)", id: "1390513012500-1") {
		createTable(tableName: "adoption_application") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "adoption_applPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "pet_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "phone", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-2") {
		createTable(tableName: "breed_suggestion") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "breed_suggestPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "soundex", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "species", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "weight", type: "int4") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-3") {
		createTable(tableName: "contract") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "contractPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "fee_cents", type: "int4")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "pdfs3key", type: "varchar(255)")

			column(name: "placement_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "rep_signature", type: "varchar(255)")

			column(name: "signature", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "signature_url", type: "varchar(255)")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-4") {
		createTable(tableName: "contract_template") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "contract_tempPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "updated_by_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-5") {
		createTable(tableName: "document") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "documentPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "content_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "file_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "s3key", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-6") {
		createTable(tableName: "event") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "eventPK")
			}

			column(name: "actor_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "hidden", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "person_id", type: "int8")

			column(name: "pet_id", type: "int8")

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-7") {
		createTable(tableName: "foster_record") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "foster_recordPK")
			}

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "foster_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp")

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "pet_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "updated_by_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-8") {
		createTable(tableName: "note") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "notePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "author_id", type: "int8")

			column(name: "content", type: "varchar(4000)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-9") {
		createTable(tableName: "org_role") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "org_rolePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "role", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-10") {
		createTable(tableName: "organization") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "organizationPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "adoption_fee_cents", type: "int4")

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated_by_id", type: "int8")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-11") {
		createTable(tableName: "person") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "adopter", type: "bool")

			column(name: "available", type: "bool")

			column(name: "company", type: "varchar(255)")

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "do_not_adopt", type: "bool")

			column(name: "email", type: "varchar(255)")

			column(name: "foster", type: "bool")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated_by_id", type: "int8")

			column(name: "linked_account_id", type: "int8")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "phone", type: "varchar(255)")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-12") {
		createTable(tableName: "person_labels") {
			column(name: "person_id", type: "int8")

			column(name: "labels_string", type: "varchar(255)")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-13") {
		createTable(tableName: "person_note") {
			column(name: "person_notes_id", type: "int8")

			column(name: "note_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-14") {
		createTable(tableName: "pet") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "petPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "age", type: "int4")

			column(name: "breed", type: "varchar(255)")

			column(name: "color", type: "varchar(255)")

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "heartworm", type: "bool")

			column(name: "housebroken", type: "bool")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated_by_id", type: "int8")

			column(name: "markings", type: "varchar(255)")

			column(name: "microchip_id", type: "varchar(255)")

			column(name: "microchipped", type: "bool")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "neutered", type: "bool")

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "placement_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "sex", type: "varchar(6)")

			column(name: "special_needs", type: "bool")

			column(name: "species", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "weight", type: "int4")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-15") {
		createTable(tableName: "pet_document") {
			column(name: "pet_documents_id", type: "int8")

			column(name: "document_id", type: "int8")

			column(name: "documents_idx", type: "int4")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-16") {
		createTable(tableName: "pet_labels") {
			column(name: "pet_id", type: "int8")

			column(name: "labels_string", type: "varchar(255)")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-17") {
		createTable(tableName: "pet_note") {
			column(name: "pet_notes_id", type: "int8")

			column(name: "note_id", type: "int8")

			column(name: "notes_idx", type: "int4")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-18") {
		createTable(tableName: "photo") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "photoPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "pet_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "is_primary", type: "bool")

			column(name: "s3key", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "standard_url", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "uploaded_by_id", type: "int8")

			column(name: "photos_idx", type: "int4")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-19") {
		createTable(tableName: "photo_cloudinary_data") {
			column(name: "cloudinary_data", type: "int8")

			column(name: "cloudinary_data_idx", type: "varchar(255)")

			column(name: "cloudinary_data_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-20") {
		createTable(tableName: "placement") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "placementPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "pet_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "with_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-21") {
		createTable(tableName: "role") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-22") {
		createTable(tableName: "seed_me_checksum") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "seed_me_checkPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "checksum", type: "varchar(255)")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "seed_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-23") {
		createTable(tableName: "transaction") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "transactionPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "amount_cents", type: "int4") {
				constraints(nullable: "false")
			}

			column(name: "category", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "entered_by_id", type: "int8")

			column(name: "last_updated_id", type: "int8")

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "pet_id", type: "int8")

			column(name: "updated_by_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-24") {
		createTable(tableName: "users") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "usersPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "activated", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "activation_code", type: "varchar(255)")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "invited_by_id", type: "int8")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)")

			column(name: "password_expired", type: "bool") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-25") {
		createTable(tableName: "users_login") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "users_loginPK")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "ip_address", type: "varchar(255)")

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "user_agent", type: "varchar(255)")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-26") {
		createTable(tableName: "users_role") {
			column(name: "user_roles_id", type: "int8")

			column(name: "role_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-77") {
		createIndex(indexName: "idx_adoptapp_pet_created", tableName: "adoption_application") {
			column(name: "pet_id")
            column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-78") {
		createIndex(indexName: "idx_suggestion_soundex_weight", tableName: "breed_suggestion") {
			column(name: "soundex")
			column(name: "weight")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-79") {
		createIndex(indexName: "placement_id_idx", tableName: "contract") {
			column(name: "placement_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-80") {
		createIndex(indexName: "organization_id_idx", tableName: "contract_template") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-81") {
		createIndex(indexName: "idx_event_organization_date_created", tableName: "event") {
            column(name: "organization_id")
			column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-82") {
		createIndex(indexName: "idx_event_person_date_created", tableName: "event") {
            column(name: "person_id")
			column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-83") {
		createIndex(indexName: "idx_event_pet_date_created", tableName: "event") {
			column(name: "pet_id")
            column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-84") {
		createIndex(indexName: "idx_orgrole_org_user", tableName: "org_role") {
			column(name: "organization_id")
			column(name: "user_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-85") {
		createIndex(indexName: "linked_account_id_idx", tableName: "person") {
			column(name: "linked_account_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-86") {
		createIndex(indexName: "person_organization_id_idx", tableName: "person") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-87") {
		createIndex(indexName: "pet_organization_id_idx", tableName: "pet") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-88") {
		createIndex(indexName: "phot_pet_id_idx", tableName: "photo") {
			column(name: "pet_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-89") {
		createIndex(indexName: "idx_placement_person_created", tableName: "placement") {
			column(name: "with_id")
            column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-90") {
		createIndex(indexName: "idx_placement_pet_created", tableName: "placement") {
            column(name: "pet_id")
			column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-91") {
		createIndex(indexName: "placement_organization_id_idx", tableName: "placement") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-92") {
		createIndex(indexName: "authority_uniq_1390513012452", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-93") {
		createIndex(indexName: "seed_name_uniq_1390513012454", tableName: "seed_me_checksum", unique: "true") {
			column(name: "seed_name")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-94") {
		createIndex(indexName: "idx_transaction_organization_date_created", tableName: "transaction") {
            column(name: "organization_id")
			column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-96") {
		createIndex(indexName: "email_uniq_1390513012456", tableName: "users", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-97") {
		createIndex(indexName: "idx_user_login_user_created", tableName: "users_login") {
            column(name: "user_id")
			column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-98") {
		createSequence(sequenceName: "hibernate_sequence")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-27") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "adoption_application", constraintName: "FK54914DE9BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-28") {
		addForeignKeyConstraint(baseColumnNames: "placement_id", baseTableName: "contract", constraintName: "FKDE3511121F6AFC87", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "placement", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-29") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "contract_template", constraintName: "FK11B801077AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-30") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "contract_template", constraintName: "FK11B80107F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-31") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "contract_template", constraintName: "FK11B801079BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-32") {
		addForeignKeyConstraint(baseColumnNames: "actor_id", baseTableName: "event", constraintName: "FK5C6729AA4092263", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-33") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "event", constraintName: "FK5C6729AF8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-34") {
		addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "event", constraintName: "FK5C6729A3FC1F3CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-35") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "event", constraintName: "FK5C6729ABCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-36") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "foster_record", constraintName: "FK1D3B0A797AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-37") {
		addForeignKeyConstraint(baseColumnNames: "foster_id", baseTableName: "foster_record", constraintName: "FK1D3B0A79B9F158CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-38") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "foster_record", constraintName: "FK1D3B0A79F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-39") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "foster_record", constraintName: "FK1D3B0A79BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-40") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "foster_record", constraintName: "FK1D3B0A799BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-41") {
		addForeignKeyConstraint(baseColumnNames: "author_id", baseTableName: "note", constraintName: "FK33AFF25E5E7B0D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-42") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "org_role", constraintName: "FK4E5C38F17AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-43") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "org_role", constraintName: "FK4E5C38F1F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-44") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "org_role", constraintName: "FK4E5C38F1FDA588CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-45") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "organization", constraintName: "FK4644ED337AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-46") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "organization", constraintName: "FK4644ED33F6D9BE54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-47") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "person", constraintName: "FKC4E39B557AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-48") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "person", constraintName: "FKC4E39B55F6D9BE54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-49") {
		addForeignKeyConstraint(baseColumnNames: "linked_account_id", baseTableName: "person", constraintName: "FKC4E39B55C2F5CD11", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-50") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "person", constraintName: "FKC4E39B55F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-51") {
		addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_labels", constraintName: "FKB8EC48293FC1F3CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-52") {
		addForeignKeyConstraint(baseColumnNames: "note_id", baseTableName: "person_note", constraintName: "FKE69F9A9C8550C36D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "note", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-53") {
		addForeignKeyConstraint(baseColumnNames: "person_notes_id", baseTableName: "person_note", constraintName: "FKE69F9A9CEF5FE8B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-54") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "pet", constraintName: "FK1B11F7AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-55") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "pet", constraintName: "FK1B11FF6D9BE54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-56") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "pet", constraintName: "FK1B11FF8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-57") {
		addForeignKeyConstraint(baseColumnNames: "placement_id", baseTableName: "pet", constraintName: "FK1B11F1F6AFC87", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "placement", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-58") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "pet_document", constraintName: "FK5FC243BA79D154D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "document", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-59") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "pet_labels", constraintName: "FKD069FB1FBCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-60") {
		addForeignKeyConstraint(baseColumnNames: "note_id", baseTableName: "pet_note", constraintName: "FK1CB993128550C36D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "note", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-61") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "photo", constraintName: "FK65B3E32BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-62") {
		addForeignKeyConstraint(baseColumnNames: "uploaded_by_id", baseTableName: "photo", constraintName: "FK65B3E323044C622", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-63") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "placement", constraintName: "FK6ADE12E57AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-64") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "placement", constraintName: "FK6ADE12E5F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-65") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "placement", constraintName: "FK6ADE12E5BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-66") {
		addForeignKeyConstraint(baseColumnNames: "with_id", baseTableName: "placement", constraintName: "FK6ADE12E55DAD1CBC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-67") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE7AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-68") {
		addForeignKeyConstraint(baseColumnNames: "entered_by_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE9B04F7F9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-69") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE54A19726", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-70") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "transaction", constraintName: "FK7FA0D2DEF8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-71") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "transaction", constraintName: "FK7FA0D2DEBCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-72") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE9BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-73") {
		addForeignKeyConstraint(baseColumnNames: "invited_by_id", baseTableName: "users", constraintName: "FK6A68E08788919FD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-74") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "users_login", constraintName: "FKF6783A72FDA588CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-75") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "users_role", constraintName: "FK9459304D587AC4ED", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1390513012500-76") {
		addForeignKeyConstraint(baseColumnNames: "user_roles_id", baseTableName: "users_role", constraintName: "FK9459304DF998028F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	include file: 'updates.groovy'
}
