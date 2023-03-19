package edu.ou.activityqueryservice.controller.post;

import edu.ou.activityqueryservice.common.constant.EndPoint;
import edu.ou.activityqueryservice.data.pojo.request.post.PostFindDetailRequest;
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
@RequestMapping(EndPoint.Post.BASE)
public class PostFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> postFindDetailService;

    /**
     * find detail of exist post
     *
     * @param postSlug slug of post
     * @return detail of exist post
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_POST)
    @GetMapping(EndPoint.Post.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailPost(
            @NotNull
            @PathVariable
            String postSlug
    ) {
        return new ResponseEntity<>(
                postFindDetailService.execute(new PostFindDetailRequest().setPostSlug(postSlug)),
                HttpStatus.OK
        );
    }
}
