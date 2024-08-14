package com.youyk.anchoreerchat.util;


import jakarta.persistence.*;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import org.assertj.core.util.introspection.CaseFormatUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component("apiDatabaseCleaner")
public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;

    private List<MappingInformation> tableNames;


    @Transactional
    public void clear() {
        this.afterPropertiesSet();
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (var mappingInformation : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + mappingInformation.tableName()).executeUpdate();
            entityManager
                    .createNativeQuery(
                            "ALTER TABLE " + mappingInformation.tableName() + " ALTER COLUMN "
                                    + mappingInformation.idName() + " RESTART WITH 1")
                    .executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void afterPropertiesSet() {
        if (tableNames != null) {
            return;
        }

        this.tableNames = entityManager.getMetamodel()
                .getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> new MappingInformation(
                        e.getJavaType().getAnnotation(Table.class).name().toUpperCase(),
                        toSnakeCase(e.getId(e.getIdType().getJavaType()).getName())
                ))
                .toList();
    }
    public static String toSnakeCase(String input) {
        return input.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }
}

record MappingInformation(String tableName, String idName) {

}
