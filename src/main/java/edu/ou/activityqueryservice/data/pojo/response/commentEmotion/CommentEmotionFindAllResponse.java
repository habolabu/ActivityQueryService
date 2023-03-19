package edu.ou.activityqueryservice.data.pojo.response.commentEmotion;

import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import lombok.Data;

@Data
public class CommentEmotionFindAllResponse implements IBaseResponse {
    private String fullName;
    private String icon;
}
