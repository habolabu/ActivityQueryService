package edu.ou.activityqueryservice.controller.commentEmotion;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.commentEmotion.CommentEmotionFindAllRequest;
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
@RequestMapping(EndPoint.CommentEmotion.BASE)
public class CommentEmotionFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> commentEmotionFindAllService;

    /**
     * Find all comment emotion with pagination
     *
     * @param commentId comment id
     * @param page      page index
     * @return list of comment emotion
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllCommentEmotion(
            @RequestParam Integer commentId,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return new ResponseEntity<>(
                commentEmotionFindAllService.execute(
                        new CommentEmotionFindAllRequest()
                                .setCommentId(commentId)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
