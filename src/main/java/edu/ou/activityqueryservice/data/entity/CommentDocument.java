package edu.ou.activityqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document("Comment")
public class CommentDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private int oId;
    private String content;
    private int totalEmotion;
    private Date createdAt;
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private int postId;
    @JsonIgnore
    private int commentId;
}
