package edu.ou.activityqueryservice.data.pojo.request.postEmotion;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PostEmotionFindAllRequest implements IBaseRequest {
    @NotNull
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer postId;
    @NotNull
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer page;
}
