package edu.ou.activityqueryservice.controller.comment;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.comment.CommentFindAllWithParamsRequest;
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
@RequestMapping(EndPoint.Comment.BASE)
public class CommentFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> commentFindAllService;

    /**
     * Find all comments with pagination
     *
     * @param postId    post id
     * @param commentId comment id
     * @param page      page index
     * @return list of comment
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllComment(
            @RequestParam Integer postId,
            @RequestParam(required = false) Integer commentId,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return new ResponseEntity<>(
                commentFindAllService.execute(
                        new CommentFindAllWithParamsRequest()
                                .setPostId(postId)
                                .setCommentId(commentId)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
