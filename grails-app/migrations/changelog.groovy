databaseChangeLog = {

	changeSet(author: "ajbrown (generated)", id: "1387173787769-1") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-2") {
		createTable(tableName: "adoption_contract") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "adoption_contPK")
			}

			column(name: "adopter_signature", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "adopter_signature_url", type: "varchar(255)")

			column(name: "adoption_fee_cents", type: "int4")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "pdfs3key", type: "varchar(255)")

			column(name: "rep_signature", type: "varchar(255)")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-3") {
		createTable(tableName: "adoption_contract_template") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "adoption_contract_templatePK")
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-4") {
		createTable(tableName: "adoption_record") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "adoption_recoPK")
			}

			column(name: "adopter_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "contract_id", type: "int8")

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

			column(name: "updated_by_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-5") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-6") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-7") {
		createTable(tableName: "dummy_domain_class") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "dummy_domain_PK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-8") {
		createTable(tableName: "event") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "eventPK")
			}

			column(name: "actor_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "hidden", type: "boolean") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-9") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-10") {
		createTable(tableName: "org_role") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "org_rolePK")
			}

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-11") {
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

			column(name: "slug", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-12") {
		createTable(tableName: "person") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "adopter", type: "boolean")

			column(name: "available", type: "boolean")

			column(name: "company", type: "varchar(255)")

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "do_not_adopt", type: "boolean")

			column(name: "email", type: "varchar(255)")

			column(name: "foster", type: "boolean")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated_by_id", type: "int8")

			column(name: "linked_account_id", type: "int8")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "notes", type: "varchar(255)")

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "phone", type: "varchar(255)")

			column(name: "slug", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-13") {
		createTable(tableName: "pet") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "petPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "adopter_id", type: "int8")

			column(name: "age", type: "int4")

			column(name: "assigned_id", type: "varchar(255)")

			column(name: "breed", type: "varchar(255)")

			column(name: "color", type: "varchar(255)")

			column(name: "created_by_id", type: "int8")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "foster_id", type: "int8")

			column(name: "heartworm", type: "boolean")

			column(name: "housebroken", type: "boolean")

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated_by_id", type: "int8")

			column(name: "markings", type: "varchar(255)")

			column(name: "microchip_id", type: "varchar(255)")

			column(name: "microchipped", type: "boolean")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "neutered", type: "boolean")

			column(name: "notes", type: "varchar(255)")

			column(name: "organization_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "sex", type: "varchar(6)")

			column(name: "slug", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "special_needs", type: "boolean")

			column(name: "status", type: "varchar(9)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "weight", type: "int4")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-14") {
		createTable(tableName: "pet_document") {
			column(name: "pet_documents_id", type: "int8")

			column(name: "document_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-15") {
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

			column(name: "is_primary", type: "boolean")

			column(name: "s3key", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "standard_url", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "uploaded_by_id", type: "int8")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-16") {
		createTable(tableName: "photo_cloudinary_data") {
			column(name: "cloudinary_data", type: "int8")

			column(name: "cloudinary_data_idx", type: "varchar(255)")

			column(name: "cloudinary_data_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-17") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-18") {
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

	changeSet(author: "ajbrown (generated)", id: "1387173787769-19") {
		createTable(tableName: "user") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "activated", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "activation_code", type: "varchar(255)")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "invited_by_id", type: "int8")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)")

			column(name: "password_expired", type: "boolean") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-64") {
		createIndex(indexName: "organization_id_idx", tableName: "event") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-65") {
		createIndex(indexName: "person_id_idx", tableName: "event") {
			column(name: "person_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-66") {
		createIndex(indexName: "pet_id_idx", tableName: "event") {
			column(name: "pet_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-67") {
		createIndex(indexName: "slug_idx", tableName: "organization") {
			column(name: "slug")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-68") {
		createIndex(indexName: "person_organization_id_idx", tableName: "person") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-69") {
		createIndex(indexName: "pet_organization_id_idx", tableName: "pet") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-70") {
		createIndex(indexName: "pet_slug_idx", tableName: "pet") {
			column(name: "slug")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-71") {
		createIndex(indexName: "authority_uniq_1387173787709", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-72") {
		createIndex(indexName: "date_created_idx", tableName: "transaction") {
			column(name: "date_created")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-73") {
		createIndex(indexName: "tx_organization_id_idx", tableName: "transaction") {
			column(name: "organization_id")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-74") {
		createIndex(indexName: "email_idx", tableName: "user") {
			column(name: "email")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-75") {
		createIndex(indexName: "email_uniq_1387173787713", tableName: "user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-76") {
		createSequence(sequenceName: "hibernate_sequence")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-20") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "adoption_application", constraintName: "FK54914DE9BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-21") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "adoption_contract_template", constraintName: "FK228B2E07AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-22") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "adoption_contract_template", constraintName: "FK228B2E0F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-23") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "adoption_contract_template", constraintName: "FK228B2E09BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-24") {
		addForeignKeyConstraint(baseColumnNames: "adopter_id", baseTableName: "adoption_record", constraintName: "FKF3268188289A5C5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-25") {
		addForeignKeyConstraint(baseColumnNames: "contract_id", baseTableName: "adoption_record", constraintName: "FKF32681834001565", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "adoption_contract", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-26") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "adoption_record", constraintName: "FKF3268187AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-27") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "adoption_record", constraintName: "FKF326818F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-28") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "adoption_record", constraintName: "FKF326818BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-29") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "adoption_record", constraintName: "FKF3268189BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-30") {
		addForeignKeyConstraint(baseColumnNames: "actor_id", baseTableName: "event", constraintName: "FK5C6729AA4092263", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-31") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "event", constraintName: "FK5C6729AF8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-32") {
		addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "event", constraintName: "FK5C6729A3FC1F3CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-33") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "event", constraintName: "FK5C6729ABCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-34") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "foster_record", constraintName: "FK1D3B0A797AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-35") {
		addForeignKeyConstraint(baseColumnNames: "foster_id", baseTableName: "foster_record", constraintName: "FK1D3B0A79B9F158CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-36") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "foster_record", constraintName: "FK1D3B0A79F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-37") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "foster_record", constraintName: "FK1D3B0A79BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-38") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "foster_record", constraintName: "FK1D3B0A799BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-39") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "org_role", constraintName: "FK4E5C38F17AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-40") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "org_role", constraintName: "FK4E5C38F1F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-41") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "org_role", constraintName: "FK4E5C38F1FDA588CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-42") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "organization", constraintName: "FK4644ED337AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-43") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "organization", constraintName: "FK4644ED33F6D9BE54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-44") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "person", constraintName: "FKC4E39B557AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-45") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "person", constraintName: "FKC4E39B55F6D9BE54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-46") {
		addForeignKeyConstraint(baseColumnNames: "linked_account_id", baseTableName: "person", constraintName: "FKC4E39B55C2F5CD11", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-47") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "person", constraintName: "FKC4E39B55F8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-48") {
		addForeignKeyConstraint(baseColumnNames: "adopter_id", baseTableName: "pet", constraintName: "FK1B11F8289A5C5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-49") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "pet", constraintName: "FK1B11F7AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-50") {
		addForeignKeyConstraint(baseColumnNames: "foster_id", baseTableName: "pet", constraintName: "FK1B11FB9F158CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-51") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "pet", constraintName: "FK1B11FF6D9BE54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-52") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "pet", constraintName: "FK1B11FF8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-53") {
		addForeignKeyConstraint(baseColumnNames: "document_id", baseTableName: "pet_document", constraintName: "FK5FC243BA79D154D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "document", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-54") {
		addForeignKeyConstraint(baseColumnNames: "pet_documents_id", baseTableName: "pet_document", constraintName: "FK5FC243B9EB21EEE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-55") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "photo", constraintName: "FK65B3E32BCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-56") {
		addForeignKeyConstraint(baseColumnNames: "uploaded_by_id", baseTableName: "photo", constraintName: "FK65B3E323044C622", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-57") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE7AC220AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-58") {
		addForeignKeyConstraint(baseColumnNames: "entered_by_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE9B04F7F9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-59") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE54A19726", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-60") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "transaction", constraintName: "FK7FA0D2DEF8F08CCD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-61") {
		addForeignKeyConstraint(baseColumnNames: "pet_id", baseTableName: "transaction", constraintName: "FK7FA0D2DEBCD71007", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pet", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-62") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE9BB0983D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "ajbrown (generated)", id: "1387173787769-63") {
		addForeignKeyConstraint(baseColumnNames: "invited_by_id", baseTableName: "user", constraintName: "FK36EBCB788919FD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
