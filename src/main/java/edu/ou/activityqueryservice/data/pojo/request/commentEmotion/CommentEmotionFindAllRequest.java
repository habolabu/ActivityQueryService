package edu.ou.activityqueryservice.data.pojo.request.commentEmotion;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CommentEmotionFindAllRequest implements IBaseRequest {
    @NotNull
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer commentId;
    @NotNull
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer page;
}
