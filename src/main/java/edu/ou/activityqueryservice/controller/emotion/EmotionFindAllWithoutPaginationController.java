package edu.ou.activityqueryservice.controller.emotion;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Emotion.BASE)
public class EmotionFindAllWithoutPaginationController {
    private final BaseService<IBaseRequest, IBaseResponse> emotionFindAllServiceWithoutPaginationService;

    /**
     * find all exist emotion without pagination
     *
     * @return list of emotion
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_EMOTION)
    @GetMapping(EndPoint.Emotion.ALL)
    public ResponseEntity<IBaseResponse> getAllEmotionWithoutPagination() {
        return new ResponseEntity<>(
                emotionFindAllServiceWithoutPaginationService.execute(null),
                HttpStatus.OK
        );
    }
}
