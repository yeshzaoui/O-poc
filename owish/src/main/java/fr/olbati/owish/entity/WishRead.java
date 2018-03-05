//package fr.olbati.owish.entity;
//
//import org.springframework.data.elasticsearch.annotations.Document;
//
//import javax.persistence.Id;
//import java.util.Date;
//
//@Document(indexName = "owish", type = "wish", shards = 1, replicas = 0)
//public class WishRead {
//
//    @Id
//    private Long id;
//
//    private String title;
//
//    private String content;
//
//    private Date createdAt;
//
//    private Date updatedAt;
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//}
