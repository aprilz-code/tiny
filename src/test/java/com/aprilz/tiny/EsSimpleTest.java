package com.aprilz.tiny;

import cn.hutool.core.lang.Assert;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.aprilz.tiny.component.es.dto.SearchInput;
import com.aprilz.tiny.component.es.service.ElasticsearchSimpleService;
import com.aprilz.tiny.component.es.util.SearchResponseUtil;
import com.aprilz.tiny.dto.QyDocument;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class EsSimpleTest {


    private static final String indexName = "simple_es1";
    @Autowired
    private ElasticsearchSimpleService simpleService;

    @Test
    public void createIndexIfNotExist() {
        boolean existIndex = simpleService.existIndex(indexName);
        if (existIndex) {
            return;
        }
        boolean createIndex = simpleService.createIndex(indexName, QyDocument.class);
    }

    @Test
    public void testCreate() {
        boolean qk1 = simpleService.createIndex(indexName);
        Assert.isTrue(qk1);
    }


    @Test
    public void bulk() {
        List<QyDocument> tList = QyDocument.mock();
        simpleService.bulk(indexName, tList);
    }

    @Test
    public void testSearch() {
        BoolQuery.Builder boolQuery = QueryBuilders.bool();
        long pageNo = 1L;
        long pageSize = 10L;
        List<String> qyidList = new ArrayList<>();
        qyidList.add("1");
        qyidList.add("2");
        qyidList.add("3");
        List<FieldValue> fieldValueList = qyidList.stream().map(FieldValue::of).collect(Collectors.toList());
        // TermsQuery.Builder termsBuilder = QueryBuilders.terms().field("qyId").terms(new TermsQueryField.Builder().value(fieldValueList).build());
        Query match = QueryBuilders.match().field("qyId").query("1").build()._toQuery();
        Query query = QueryBuilders.matchPhrase().field("qyMc").query("企业1").build()._toQuery();
        boolQuery.must(match);
        // boolQuery.must(termsBuilder.build()._toQuery());
        SearchInput searchInput = new SearchInput();
        searchInput.setIndexName("aaa");
        searchInput.setQuery(boolQuery.build()._toQuery());
        searchInput.setTClass(QyDocument.class);
        searchInput.setFrom((int) ((pageNo - 1) * pageSize));
        searchInput.setSize((int) pageSize);
        SearchResponse<QyDocument> search = simpleService.search(searchInput);
        List<QyDocument> collect = search.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
        collect.forEach(System.out::println);


    }

    @Test
    public void testAggregation() {
        BoolQuery.Builder boolQuery = QueryBuilders.bool();
        long pageNo = 1L;
        long pageSize = 10L;
        List<String> qyidList = new ArrayList<>();
        qyidList.add("4");
        qyidList.add("5");
        qyidList.add("10");
        qyidList.add("11");
        qyidList.add("12");
        qyidList.add("13");
        qyidList.add("14");

        List<FieldValue> fieldValueList = qyidList.stream().map(FieldValue::of).collect(Collectors.toList());
        TermsQuery.Builder termsBuilder = QueryBuilders.terms().field("qyId").terms(new TermsQueryField.Builder().value(fieldValueList).build());
        boolQuery.must(termsBuilder.build()._toQuery());
        SearchInput searchInput = new SearchInput();
        searchInput.setIndexName("bbb");
        searchInput.setQuery(boolQuery.build()._toQuery());
        searchInput.setTClass(QyDocument.class);
        searchInput.setFrom((int) ((pageNo - 1) * pageSize));
        searchInput.setSize((int) pageSize);

        searchInput.addStringTermsTypeAggregation("qyMcAgg", "qyMc", 50);
        SearchResponse<QyDocument> searchResponse = simpleService.search(searchInput);

        Map<Object, Long> result = SearchResponseUtil.readStreamTypeAggregation(searchResponse, "qyMcAgg");
        result.forEach((k, v) -> System.out.println(k.toString() + " " + v));

    }

}
