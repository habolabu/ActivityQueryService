package edu.ou.activityqueryservice.data.pojo.request.feedBackType;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class FeedBackTypeFindAllRequest implements IBaseRequest {
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer page;
    private String name;
}
