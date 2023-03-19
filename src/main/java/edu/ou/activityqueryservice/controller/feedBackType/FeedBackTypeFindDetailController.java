package edu.ou.activityqueryservice.controller.feedBackType;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.feedBack.FeedBackFindDetailRequest;
import edu.ou.activityqueryservice.data.pojo.request.feedBackType.FeedBackTypeFindDetailRequest;
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
@RequestMapping(EndPoint.FeedBackType.BASE)
public class FeedBackTypeFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> feedBackTypeFindDetailService;

    /**
     * find detail of exist
     *
     * @param feedBackTypeSlug slug of feedback type
     * @return detail of exist feedback type
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_FEEDBACK_TYPE)
    @GetMapping(EndPoint.FeedBackType.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailFeedBackType(
            @NotNull
            @PathVariable
            String feedBackTypeSlug
    ) {
        return new ResponseEntity<>(
                feedBackTypeFindDetailService.execute(new FeedBackTypeFindDetailRequest().setFeedBackTypeSlug(feedBackTypeSlug)),
                HttpStatus.OK
        );
    }
}
