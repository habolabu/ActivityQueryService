package edu.ou.activityqueryservice.controller.feedBackType;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.feedBackType.FeedBackTypeFindAllRequest;
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
@RequestMapping(EndPoint.FeedBackType.BASE)
public class FeedBackTypeFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> feedBackTypeFindAllService;

    /**
     * find all exist feedback type
     *
     * @param page page index
     * @param name name of feedback type
     * @return list of feedback type
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_FEEDBACK_TYPE)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllFeedBackType(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name
    ) {
        return new ResponseEntity<>(
                feedBackTypeFindAllService.execute(
                        new FeedBackTypeFindAllRequest()
                                .setName(name)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
