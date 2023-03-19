package edu.ou.activityqueryservice.controller.feedBack;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.feedBack.FeedBackFindDetailRequest;
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
@RequestMapping(EndPoint.FeedBack.BASE)
public class FeedBackFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> feedBackFindDetailService;

    /**
     * find detail of exist
     *
     * @param feedBackSlug id of feedback
     * @return detail of exist feedback
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_FEEDBACK)
    @GetMapping(EndPoint.FeedBack.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailFeedBack(
            @NotNull
            @PathVariable
            String feedBackSlug
    ) {
        return new ResponseEntity<>(
                feedBackFindDetailService.execute(new FeedBackFindDetailRequest().setFeedBackSlug(feedBackSlug)),
                HttpStatus.OK
        );
    }
}
