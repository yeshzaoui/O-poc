package fr.olbati.owish.service.implemention;

import fr.olbati.owish.bean.WishReadBean;
import fr.olbati.owish.bean.WishWriteBean;
import fr.olbati.owish.config.ElasticClient;
import fr.olbati.owish.entity.Wish;
import fr.olbati.owish.mapper.WishMapper;
import fr.olbati.owish.repository.WishRepository;
import fr.olbati.owish.service.WishService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WishServiceImpl implements WishService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WishRepository wishRepository;

//    @Autowired
//    private WishReadRepository wishReadRepository;

//    @Override
//    public List<WishReadBean> getAll() {
//        logger.info("Start getAll");
//        List<Wish> wishes = Lists.newArrayList(wishReadRepository.findAll());
//        List<WishReadBean> wishReadBeans = WishMapper.toBeans(wishes);
//        logger.info("End getAll size " + wishReadBeans.size());
//        return wishReadBeans;
//    }

    @Override
    public List<WishReadBean> getAll() throws UnknownHostException {
        ElasticClient elasticClient = new ElasticClient();
        Client client = elasticClient.getClient();
        SearchResponse response = null;
        try {
            response = client.prepareSearch().setIndices("owish")
                    .setTypes("wish")
                    .addSort("created_at", SortOrder.DESC)
                    .execute().actionGet();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        List<WishReadBean> wishReadBeans = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSourceAsMap();
            WishReadBean wishReadBean = new WishReadBean();
            Integer id = (Integer) source.get("id");
            wishReadBean.setId(id.longValue());
            wishReadBean.setTitle((String) source.get("title"));
            wishReadBean.setContent((String) source.get("content"));
            wishReadBean.setCreatedAt((String) source.get("created_at"));
            wishReadBean.setUpdatedAt((String) source.get("updated_at"));
            wishReadBeans.add(wishReadBean);
        }

        return wishReadBeans;

    }

    @Override
    public WishReadBean add(WishWriteBean wishWriteBean) {
        logger.info("Start add method with title = '{}', content = '{}'", wishWriteBean.getTitle(), wishWriteBean.getContent());
        Wish wish = wishRepository.save(WishMapper.toEntity(wishWriteBean));
        logger.info("End add method");
        return WishMapper.toBean(wish);
    }

    @Override
    public WishReadBean getById(Long id) {
        logger.info("Start getById method with id '{}'", id);
        Wish wish = wishRepository.findOne(id);
        if(wish == null) {
            throw new EntityNotFoundException("There is no wish entity with such ID in the database.");
        }
        logger.info("End getById method");
        return WishMapper.toBean(wish);
    }

    @Override
    public WishReadBean edit(Long id, WishWriteBean wishWriteBean) {
        logger.info("Start edit method with id '{}'", id);
        Wish wish = wishRepository.findOne(id);
        if(wish == null) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        wish.setTitle(wishWriteBean.getTitle());
        wish.setContent(wishWriteBean.getContent());
        logger.info("End edit method");
        return WishMapper.toBean(wishRepository.save(wish));
    }

    @Override
    public void remove(Long id) {
        logger.info("Start remove method with id '{}'", id);
        wishRepository.delete(id);
        logger.info("End remove method");
    }
}
