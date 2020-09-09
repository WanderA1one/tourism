package com.service.impl;

import com.pojo.Customer;
import com.pojo.ResultList;
import com.service.SearchCustomerService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SearchCustomerServiceImpl implements SearchCustomerService {
    @Autowired
    RestHighLevelClient client;

    @Override
    public ResultList<Customer> searchCustomer(String index,Integer page, Integer size) throws IOException {
        //DSL搜索
        SearchRequest searchRequest = new SearchRequest("customer-project");
        searchRequest.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(index,"customerName","customerCardId").field("customerName",10));

        //设置分页
        int from = (page-1)*size;

        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("customerName"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("customerCardId"));
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);

        SearchHits hits = search.getHits();

        SearchHit[] hits1 = hits.getHits();
        List list = new ArrayList<>();
        for (SearchHit his:hits1) {
            Customer customer = new Customer();
            //TbShop tbShop = new TbShop();
            Map<String, Object> sourceAsMap = his.getSourceAsMap();
            //tbShop.setShopName();
            String customerName = (String) sourceAsMap.get("customerName");
            String customerCardId = (String) sourceAsMap.get("customerCardId");
            Map<String, HighlightField> shopName1 = his.getHighlightFields();
            if (shopName1!=null){
                HighlightField shopName2 = shopName1.get("customerName");
                if (shopName2!=null){
                    Text[] fragments = shopName2.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text na:fragments) {
                        stringBuffer.append(na);
                    }
                    customerName = stringBuffer.toString();
                }
                HighlightField shopDesc1 = shopName1.get("customerCardId");
                if (shopDesc1!=null){
                    Text[] fragments = shopDesc1.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text na:fragments) {
                        stringBuffer.append(na);
                    }
                    customerCardId = stringBuffer.toString();
                }
            }
            customer.setCustomerName(customerName);
            customer.setCustomerCardId(customerCardId);
            customer.setCustomerPhone((String) sourceAsMap.get("customerPhone"));
            customer.setCustomerId((String) sourceAsMap.get("customerId"));
            customer.setCustomerPassword((String) sourceAsMap.get("customerPassword"));
            customer.setCustomerRole((Integer) sourceAsMap.get("customerRole"));
            customer.setCustomerSex((Integer) sourceAsMap.get("customerSex"));
            customer.setCustomerAge((Integer)sourceAsMap.get("customerAge"));
            //customer.setCustomerTime((Date) sourceAsMap.get("customerTime"));
            customer.setCustomerImg((String) sourceAsMap.get("customerImg"));
            list.add(customer);
        }
        ResultList base = new ResultList<>();
        base.setList(list);
        base.setTotal(hits.getTotalHits());
        return base;
    }
}
