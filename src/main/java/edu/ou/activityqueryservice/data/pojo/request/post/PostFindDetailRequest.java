package edu.ou.activityqueryservice.data.pojo.request.post;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostFindDetailRequest implements IBaseRequest {
    @NotBlank
    private String postSlug;
}
