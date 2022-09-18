package com.ecobenchmark.controllers.getlists;

import org.hibernate.transform.ResultTransformer;

import java.time.Instant;
import java.util.*;

public class ListResponseTransformer
        implements ResultTransformer {

    private final transient Map<UUID, ListResponse> listResponseMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] objects, String[] strings) {
        Map<String, Integer> aliasToIndexMap = aliasToIndexMap(strings);

        UUID listId = (UUID) objects[aliasToIndexMap.get("id")];

        ListResponse list = listResponseMap.computeIfAbsent(
                listId,
                id -> contructListResponse(objects, aliasToIndexMap)
        );

        list.getTasks().add(
                contructTaskResponse(objects, aliasToIndexMap)
        );

        return list;
    }

    private ListResponse contructListResponse(Object[] tuples, Map<String, Integer> aliasToIndexMap) {
        return new ListResponse((UUID) tuples[aliasToIndexMap.get("id")], (String) tuples[aliasToIndexMap.get("name")], (Instant) tuples[aliasToIndexMap.get("creation_date")], (UUID) tuples[aliasToIndexMap.get("account_id")]);
    }

    private TaskResponse contructTaskResponse(Object[] tuples, Map<String, Integer> aliasToIndexMap){
        return new TaskResponse((UUID) tuples[aliasToIndexMap.get("task_id")], (String) tuples[aliasToIndexMap.get("task_name")], (String) tuples[aliasToIndexMap.get("task_description")], (Instant) tuples[aliasToIndexMap.get("task_creation_date")]);
    }

    @Override
    public List<ListResponse> transformList(List list) {
        return new ArrayList<>(listResponseMap.values());
    }

    public  Map<String, Integer> aliasToIndexMap(
            String[] aliases) {

        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(aliases[i], i);
        }

        return aliasToIndexMap;
    }
}
