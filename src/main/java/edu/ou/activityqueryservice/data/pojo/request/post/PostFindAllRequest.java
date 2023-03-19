package edu.ou.activityqueryservice.data.pojo.request.post;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
public class PostFindAllRequest implements IBaseRequest {
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer page;
    private String title;
    private Integer bTotalEmotion;
    private Integer eTotalEmotion;
    private Integer bTotalComment;
    private Integer eTotalComment;
    private Boolean isEdited;
    private Date bCreatedAt;
    private Date eCreatedAt;
}
