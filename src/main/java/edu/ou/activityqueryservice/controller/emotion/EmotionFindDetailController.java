package edu.ou.activityqueryservice.controller.emotion;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.emotion.EmotionFindDetailRequest;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Emotion.BASE)
public class EmotionFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> emotionFindDetailService;

    /**
     * find detail of exist emotion
     *
     * @param emotionId id of emotion
     * @return detail of exist emotion
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_EMOTION)
    @GetMapping(EndPoint.Emotion.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailEmotion(
            @NotNull
            @PathVariable
            int emotionId
    ) {
        return new ResponseEntity<>(
                emotionFindDetailService.execute(new EmotionFindDetailRequest().setEmotionId(emotionId)),
                HttpStatus.OK
        );
    }
}
