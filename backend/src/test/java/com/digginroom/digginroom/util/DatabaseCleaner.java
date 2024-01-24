package com.digginroom.digginroom.util;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseCleaner {

    @Autowired
    private EntityManager entityManager;
    private List<String> tableNames;
    private final Set<DatabaseTableName> filterDatabaseTableNames;

    public DatabaseCleaner(final Set<DatabaseTableName> filterDatabaseTableNames) {
        this.filterDatabaseTableNames = filterDatabaseTableNames;
    }

    @PostConstruct
    public void setUpDatabaseTableNames() {
        this.tableNames = getTableNames();
    }

    private List<String> getTableNames() {
        return getTableMetaData().stream()
                .map(this::getTableName)
                .filter(tableName -> !filterDatabaseTableNames.contains(DatabaseTableName.of(tableName)))
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> getTableMetaData() {
        return entityManager.createNativeQuery("SHOW TABLES").getResultList();
    }

    private String getTableName(final Object[] tableInfos) {
        return (String) tableInfos[0];
    }

    @Transactional
    public void clear() {
        executeQuery("SET REFERENTIAL_INTEGRITY FALSE;");
        tableNames.forEach(tableName -> executeQuery("TRUNCATE TABLE " + tableName + " RESTART IDENTITY;"));
        executeQuery("SET REFERENTIAL_INTEGRITY TRUE;");
    }

    private void executeQuery(final String query) {
        entityManager.createNativeQuery(query).executeUpdate();
    }
}
