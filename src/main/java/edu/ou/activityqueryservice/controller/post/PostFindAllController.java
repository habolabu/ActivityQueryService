package edu.ou.activityqueryservice.controller.post;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.post.PostFindAllRequest;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Post.BASE)
public class PostFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> postFindAllService;

    /**
     * find all exist post
     *
     * @param page page index
     * @return list of post
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_POST)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllPost(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer bTotalEmotion,
            @RequestParam(required = false) Integer eTotalEmotion,
            @RequestParam(required = false) Integer bTotalComment,
            @RequestParam(required = false) Integer eTotalComment,
            @RequestParam(required = false) Boolean isEdited,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)
            Date bCreatedAt,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)
            Date eCreatedAt
    ) {
        return new ResponseEntity<>(
                postFindAllService.execute(
                        new PostFindAllRequest()
                                .setTitle(title)
                                .setBTotalEmotion(bTotalEmotion)
                                .setETotalEmotion(eTotalEmotion)
                                .setBTotalComment(bTotalComment)
                                .setETotalComment(eTotalComment)
                                .setIsEdited(isEdited)
                                .setBCreatedAt(bCreatedAt)
                                .setECreatedAt(eCreatedAt)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
