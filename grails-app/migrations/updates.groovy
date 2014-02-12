databaseChangeLog = {
    changeSet(author: "ajbrown (generated)", id: "1390526397682-1") {
        createTable(tableName: "name_suggestion") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "name_suggestiPK")
            }
            column(name: "version", type: "int8") {
                constraints(nullable: "false")
            }
            column(name: "metaphone", type: "varchar(255)")
            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
            column(name: "sex", type: "varchar(6)") {
                constraints(nullable: "false")
            }

            column(name: "soundex", type: "varchar(255)")
            column(name: "weight", type: "int4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390526397682-2") {
        addColumn(tableName: "breed_suggestion") {
            column(name: "metaphone", type: "varchar(255)")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390526397682-3") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "soundex", tableName: "breed_suggestion")
    }

    changeSet(author: "ajbrown (generated)", id: "1390526397682-4") {
        createIndex(indexName: "breed_suggestion_unique_name", tableName: "breed_suggestion", unique: "true") {
            column(name: "species")
            column(name: "name")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390526397682-5") {
        createIndex(indexName: "idx_name_suggestion_metaphone", tableName: "name_suggestion") {
            column(name: "metaphone")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390526397682-6") {
        createIndex(indexName: "idx_name_suggestion_soundex", tableName: "name_suggestion") {
            column(name: "soundex")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390526397682-7") {
        createIndex(indexName: "name_suggestion_unique_name", tableName: "name_suggestion", unique: "true") {
            column(name: "sex")
            column(name: "name")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390552724161-1") {
        createTable(tableName: "contract_template_terms") {
            column(name: "contract_template_id", type: "int8")
            column(name: "term", type: "varchar(2000)")
            column(name: "list_order", type: "int4")
        }
        createIndex(indexName: "contract_template_id_idx", tableName: "contract_template_terms") {
            column(name: "contract_template_id")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390553068523-3") {
        createIndex(indexName: "person_labels_idx", tableName: "person_labels") {
            column(name: "person_id")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390553068523-4") {
        createIndex(indexName: "person_notes_idx", tableName: "person_note") {
            column(name: "person_notes_id")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390553068523-5") {
        createIndex(indexName: "pet_labels_idx", tableName: "pet_labels") {
            column(name: "pet_id")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390553068523-6") {
        createIndex(indexName: "pet_notes_idx", tableName: "pet_note") {
            column(name: "pet_notes_id")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390553068523-7") {
        createIndex(indexName: "pet_documents_idx", tableName: "pet_document") {
            column(name: "pet_documents_id")
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-1") {
        addColumn(tableName: "users_role") {
            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-2") {
        addColumn(tableName: "users_role") {
            column(name: "user_id", type: "int8") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-3") {
        addNotNullConstraint(columnDataType: "int8", columnName: "role_id", tableName: "users_role")
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-4") {
        addPrimaryKey(columnNames: "role_id, user_id", constraintName: "users_rolePK", tableName: "users_role")
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-5") {
        dropForeignKeyConstraint(baseTableName: "users_role", baseTableSchemaName: "public", constraintName: "fk9459304df998028f")
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-7") {
        dropColumn(columnName: "user_roles_id", tableName: "users_role")
    }

    changeSet(author: "ajbrown (generated)", id: "1390635252241-6") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "users_role", constraintName: "FK9459304DFDA588CD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
    }
}