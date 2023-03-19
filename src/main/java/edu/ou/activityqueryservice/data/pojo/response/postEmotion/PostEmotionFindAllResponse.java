package edu.ou.activityqueryservice.data.pojo.response.postEmotion;

import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import lombok.Data;

@Data
public class PostEmotionFindAllResponse implements IBaseResponse {
    private String fullName;
    private String icon;
}
