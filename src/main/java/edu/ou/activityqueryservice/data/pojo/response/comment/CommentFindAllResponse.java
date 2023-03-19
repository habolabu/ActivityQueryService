package edu.ou.activityqueryservice.data.pojo.response.comment;

import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import lombok.Data;

import java.util.Date;

@Data
public class CommentFindAllResponse implements IBaseResponse {
    private int id;
    private String content;
    private int totalEmotion;
    private Date createdAt;
    private String fullName;

}
