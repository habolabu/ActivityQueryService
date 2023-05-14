package edu.ou.activityqueryservice.service.comment;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.CommentDocument;
import edu.ou.activityqueryservice.data.pojo.request.comment.CommentFindAllWithParamsRequest;
import edu.ou.activityqueryservice.data.pojo.response.comment.CommentFindAllResponse;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.queue.human.external.user.UserFindDetailByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<CommentDocument>> commentFindAllRepository;
    private final IBaseRepository<Query, Integer> commentGetPageAmountRepository;
    private final ValidValidation validValidation;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, CommentFindAllWithParamsRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "comment parameters"
            );
        }
    }

    /**
     * Find all comment with pagination
     *
     * @param request request
     * @return comment list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final CommentFindAllWithParamsRequest commentFindAllWithParamsRequest =
                (CommentFindAllWithParamsRequest) request;

        final Query query = this.filter(commentFindAllWithParamsRequest);
        final List<CommentDocument> commentDocuments = commentFindAllRepository.execute(query);
        final int pageAmount = commentGetPageAmountRepository.execute(query.skip(0).limit(0));

        final List<CommentFindAllResponse> commentFindAllResponses = new ArrayList<>();
        commentDocuments.forEach(commentDocument -> {
            final Object userInfo = rabbitTemplate.convertSendAndReceive(
                    UserFindDetailByIdQueueE.EXCHANGE,
                    UserFindDetailByIdQueueE.ROUTING_KEY,
                    commentDocument.getUserId()
            );
            final Map<String, Object> userInfoData = (Map<String, Object>) userInfo;

            commentFindAllResponses.add(
                    new CommentFindAllResponse()
                            .setId(commentDocument.getOId())
                            .setCreatedAt(commentDocument.getCreatedAt())
                            .setTotalEmotion(commentDocument.getTotalEmotion())
                            .setContent(commentDocument.getContent())
                            .setFullName(
                                    String.format(
                                            "%s %s",
                                            userInfoData.get("lastName"),
                                            userInfoData.get("firstName")
                                    )
                            )
            );
        });

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", commentFindAllResponses,
                                "totalPage", PaginationUtils.getPageAmount(pageAmount)
                        ),
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }

    /**
     * Filter response
     *
     * @param request request params
     * @author Nguyen Trung Kien - OU
     */
    private Query filter(CommentFindAllWithParamsRequest request) {
        final Query query = new Query();
        if (Objects.nonNull(request.getCommentId())) {
            query.addCriteria(
                    Criteria.where("commentId")
                            .is(request.getCommentId())
            );
        } else {
            query.addCriteria(
                    Criteria.where("commentId")
                            .isNull()
            );
        }

        if (Objects.nonNull(request.getPostId())) {
            query.addCriteria(
                    Criteria.where("postId")
                            .is(request.getPostId())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
