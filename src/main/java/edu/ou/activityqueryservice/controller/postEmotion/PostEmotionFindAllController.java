package edu.ou.activityqueryservice.controller.postEmotion;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.postEmotion.PostEmotionFindAllRequest;
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
@RequestMapping(EndPoint.PostEmotion.BASE)
public class PostEmotionFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> postEmotionFindAllService;

    /**
     * Find all post emotion with pagination
     *
     * @param postId post id
     * @param page   page index
     * @return list of post emotion
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_POST_EMOTION)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllPostEmotion(
            @RequestParam Integer postId,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return new ResponseEntity<>(
                postEmotionFindAllService.execute(
                        new PostEmotionFindAllRequest()
                                .setPostId(postId)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
