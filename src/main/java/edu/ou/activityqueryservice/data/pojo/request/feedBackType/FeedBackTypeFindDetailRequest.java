package edu.ou.activityqueryservice.data.pojo.request.feedBackType;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FeedBackTypeFindDetailRequest implements IBaseRequest {
    @NotBlank
    private String feedBackTypeSlug;
}
