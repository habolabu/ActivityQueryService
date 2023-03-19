package edu.ou.activityqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("CommentEmotion")
public class CommentEmotionDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private int oId;
    @JsonIgnore
    private int commentId;
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private int emotionId;

}
