package edu.ou.activityqueryservice.data.pojo.request.feedBack;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FeedBackFindDetailRequest implements IBaseRequest {
    @NotBlank
    private String feedBackSlug;
}
