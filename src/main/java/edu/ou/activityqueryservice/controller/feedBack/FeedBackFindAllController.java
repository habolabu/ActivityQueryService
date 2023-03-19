package edu.ou.activityqueryservice.controller.feedBack;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.feedBack.FeedBackFindAllRequest;
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
@RequestMapping(EndPoint.FeedBack.BASE)
public class FeedBackFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> feedBackFindAllService;

    /**
     * find all exist feedback
     *
     * @param page           page index
     * @param title          title of feedback
     * @param feedBackTypeId feedback type id
     * @return list of feedback
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_FEEDBACK)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllFeedBack(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer feedBackTypeId
    ) {
        return new ResponseEntity<>(
                feedBackFindAllService.execute(
                        new FeedBackFindAllRequest()
                                .setTitle(title)
                                .setFeedBackTypeId(feedBackTypeId)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
