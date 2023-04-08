package com.aprilz.tiny;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import com.alibaba.fastjson.JSON;
import com.aprilz.tiny.dto.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class UnitTest {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void createIndex() throws IOException {
        String indexName = "es_index_name";
        String aliasIndexName = "es_alias_index_name";

        Map<String, Property> propertyMap = new HashMap<>();
        propertyMap.put("name",new Property(new TextProperty.Builder().index(true).store(true).build()));
        propertyMap.put("age",new Property(new IntegerNumberProperty.Builder().index(false).build()));
        propertyMap.put("sex",new Property(new BooleanProperty.Builder().index(false).build()));

        TypeMapping typeMapping = new TypeMapping.Builder().properties(propertyMap).build();
        IndexSettings indexSettings = new IndexSettings.Builder().numberOfShards(String.valueOf(1)).numberOfReplicas(String.valueOf(0)).build();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                .index(indexName)
                //设置别名
                .aliases(aliasIndexName, new Alias.Builder().isWriteIndex(true).build())
                .mappings(typeMapping)
                .settings(indexSettings)
                .build();

        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(createIndexRequest);
        System.out.println(createIndexResponse.acknowledged());
    }

    @Test
    public void getIndexWithMappingsAndSettings() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index("es_index_name").build();
        GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(getIndexRequest);

        IndexState es_index_name = getIndexResponse.get("es_index_name");

        System.out.println(JSON.toJSONString(es_index_name.mappings().properties()));
        System.out.println(JSON.toJSONString(es_index_name.aliases()));
        System.out.println(JSON.toJSONString(es_index_name.settings()));
    }


    @Test
    public void deleteIndex() throws IOException {
        List<String> indexList = Collections.singletonList("es_index_name");
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index(indexList).build();
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices().delete(deleteIndexRequest);
        System.out.println(deleteIndexResponse.acknowledged());
    }


    @Test
    public void existsDocument() throws IOException {
        GetRequest getRequest = new GetRequest.Builder().index("person").id("4").build();
        GetResponse<Person> personGetResponse = elasticsearchClient.get(getRequest, Person.class);

        System.out.println(personGetResponse.source());
        System.out.println(personGetResponse.found());
    }

    @Test
    public void saveDocument() throws IOException {
        Person person = new Person();
        person.setName("小奇");
        person.setAge(1);
        person.setSex("男");
        IndexRequest<Person> personIndexRequest = new IndexRequest.Builder<Person>().index("person").id("7").document(person).build();

        IndexResponse indexResponse = elasticsearchClient.index(personIndexRequest);

        //结果成功为：Created
        System.out.println(indexResponse.result());
    }

    @Test
    public void updateDocument() throws IOException {
        Person person = new Person();
//        person.setNickname("里斯kitty");
        person.setName("里斯1234");
//
//        person.setSex(true);
//        person.setAge(28);
//        person.setSex(true);
        UpdateRequest<Person, Person> personPersonUpdateRequest = new UpdateRequest.Builder<Person, Person>().index("person").id("4").doc(person).build();

        UpdateResponse<Person> personUpdateResponse = elasticsearchClient.update(personPersonUpdateRequest, Person.class);

        //结果成功为：Updated
        System.out.println(personUpdateResponse.result());
    }


    @Test
    public void getDocumentById() throws IOException {
        GetRequest getRequest = new GetRequest.Builder().index("person").id("7").build();

        GetResponse<Person> personGetResponse = elasticsearchClient.get(getRequest, Person.class);

        System.out.println(personGetResponse.source());
        System.out.println(personGetResponse.id());
    }

    @Test
    public void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index("person").build();
        SearchResponse<Person> personSearchResponse = elasticsearchClient.search(searchRequest, Person.class);
        List<Hit<Person>> hits = personSearchResponse.hits().hits();
        hits.forEach(hit ->{
            System.out.println(hit.source());
        });
    }

    @Test
    public void searchByPages() throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index("person").from(0).size(10).build();


        SearchResponse<Person> personSearchResponse = elasticsearchClient.search(searchRequest, Person.class);
        List<Hit<Person>> hits = personSearchResponse.hits().hits();
        hits.forEach(hit ->{
            System.out.println(hit.source());
        });
    }
}
