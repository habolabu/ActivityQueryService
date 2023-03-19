package edu.ou.activityqueryservice.data.pojo.request.emotion;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class EmotionFindDetailRequest implements IBaseRequest {
    @Min(
            value = 1,
            message = "The page value must greater or equals than 1"
    )
    private Integer emotionId;
}
