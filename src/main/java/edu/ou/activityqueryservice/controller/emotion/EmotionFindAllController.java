package edu.ou.activityqueryservice.controller.emotion;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.emotion.EmotionFindAllRequest;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Emotion.BASE)
public class EmotionFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> emotionFindAllService;

    /**
     * find all exist emotion
     *
     * @param page    page index
     * @return list of emotion
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_EMOTION)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllEmotion(
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return new ResponseEntity<>(
                emotionFindAllService.execute(new EmotionFindAllRequest().setPage(page)),
                HttpStatus.OK
        );
    }
}
