package com.snowwave.textsearch.service;

import com.snowwave.textsearch.model.SearchDTO;
import com.snowwave.textsearch.model.TextDTO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by zhangfuqiang on 2018/5/25.
 */
@Service
public class SearchService {

    @Autowired
    private TransportClient transportClient;

    /**
     *全文查询
     * @param keyword
     * @return
     */
    public SearchDTO searchAll(String keyword) {
        // 查询索引
        SearchRequestBuilder search = transportClient.prepareSearch("text");

        QueryStringQueryBuilder qs = new QueryStringQueryBuilder(keyword);
        // 最匹配的在前
        qs.minimumShouldMatch("100%");
        qs.useDisMax(true);
        search.setQuery(qs);
        SearchResponse response = search.get();

        //查询到的数据是一个list
        List<TextDTO> textDTOList = new ArrayList<>();
        // 将HIT转对象
        TextDTO textDTO;
        for(SearchHit hit : response.getHits()) {
            textDTO = new TextDTO();
            textDTO.setId(hit.getId());
            textDTO.setType(hit.getType());
            Map<String, Object> source = hit.getSource();
            String title = (String) source.get("title");
            String author = (String) source.get("author");
            String date = (String) source.get("update_time");
            String text = (String) source.get("TextContent");
            textDTO.setTitle(title);
            textDTO.setAuthor(author);
            textDTO.setUpdate_time(date);
            textDTO.setTextContent(text);
            textDTOList.add(textDTO);
        }
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTotal(textDTOList.size());
        searchDTO.setTextDTOList(textDTOList);
        return searchDTO;
    }
}
