package org.technology.consilium.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class GraphqlController {

    private final GraphQL graphQL;

    private final ObjectMapper objectMapper;

    @Autowired
    public GraphqlController(GraphQL graphQL, ObjectMapper objectMapper){
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(
            value = "/graphql",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin
    public Map<String, Object> graphqlGET(@RequestParam("query") String query,
                                          @RequestParam(value = "operationName", required = false) String operationName,
                                          @RequestParam("variables") String variablesJson) {
        query = Objects.requireNonNullElse(query, "");

        Map<String, Object> variables = new LinkedHashMap<>();
        if(Objects.nonNull(variablesJson)) {
            try {
                variables = objectMapper.readValue(variablesJson, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.error("Failed to interpret JSON: " + variablesJson);
            }
        }
        return executeGraphqlQuery(operationName, query, variables);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/graphql",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Map<String, Object> graphql(@RequestBody Map<String, Object> body) {
        String query = (String) body.get("query");
        query = Objects.requireNonNullElse(query, "");
        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        variables = Objects.requireNonNullElse(variables, new LinkedHashMap<>());
        return executeGraphqlQuery(operationName, query, variables);
    }

    private Map<String, Object> executeGraphqlQuery(String operationName,
                                                    String query, Map<String, Object> variables) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName(operationName)
                .build();

        return graphQL.execute(executionInput).toSpecification();
    }
}
