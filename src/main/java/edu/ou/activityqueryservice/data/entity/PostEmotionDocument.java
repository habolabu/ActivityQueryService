package edu.ou.activityqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("PostEmotion")
public class PostEmotionDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonIgnore
    private int postId;
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private int emotionId;
}
