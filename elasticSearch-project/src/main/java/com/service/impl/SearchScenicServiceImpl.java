package com.service.impl;

import com.pojo.ResultList;
import com.pojo.ScenicSpot;
import com.service.SearchScenicService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchScenicServiceImpl implements SearchScenicService {

    @Autowired
    RestHighLevelClient client;

    @Override
    public ResultList<ScenicSpot> searchScenic(String index) throws IOException {
        //DSL搜索
//        SearchRequest searchRequest = new SearchRequest("scenicSpot-project");
        SearchRequest searchRequest = new SearchRequest("scenic");
        searchRequest.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(index,"scenicSpotName","scenicSpotDesc").field("scenicSpotName",10));

//        //设置分页
//        int from=(page-1)*size;
//        searchSourceBuilder.from(from);
//        searchSourceBuilder.size(size);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");

        highlightBuilder.fields().add(new HighlightBuilder.Field("scenicSpotName"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("scenicSpotDesc"));
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();

        List list = new ArrayList<>();

        for (SearchHit his : hits1) {

            ScenicSpot scenicSpot = new ScenicSpot();
            Map<String, Object> sourceAsMap = his.getSourceAsMap();
            String scenicSpotName = (String) sourceAsMap.get("scenicSpotName");
            String scenicSpotDesc = (String) sourceAsMap.get("scenicSpotDesc");

            Map<String, HighlightField> scenicName = his.getHighlightFields();

            if(scenicName!=null){
                HighlightField scenicName1 = scenicName.get("scenicSpotName");
                if(scenicName1!=null){
                    Text[] fragments = scenicName1.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text na : fragments) {
                        stringBuffer.append(na);
                    }
                    scenicSpotName=stringBuffer.toString();
                }
                HighlightField scenicDesc1 = scenicName.get("scenicSpotDesc");
                if(scenicDesc1!=null){
                    Text[] fragments = scenicDesc1.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text na:fragments) {
                        stringBuffer.append(na);
                    }
                    scenicSpotDesc=stringBuffer.toString();
                }
            }
            scenicSpot.setScenicSpotName(scenicSpotName);
            scenicSpot.setScenicSpotDesc(scenicSpotDesc);
            scenicSpot.setScenicSpotId((String) sourceAsMap.get("scenicSpotId"));
            scenicSpot.setScenicSpotAddr((String) sourceAsMap.get("scenicSpotAddr"));
            scenicSpot.setScenicSpotScore((Double) sourceAsMap.get("scenicSpotScore"));
            scenicSpot.setScenicSqotPrice((String) sourceAsMap.get("scenicSqotPrice"));
            list.add(scenicSpot);
        }
        ResultList base = new ResultList<>();
        base.setList(list);
        base.setTotal(hits.getTotalHits());

        return base;
    }
}
