package edu.ou.activityqueryservice.service.commentEmotion;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.CommentEmotionDocument;
import edu.ou.activityqueryservice.data.entity.EmotionDocument;
import edu.ou.activityqueryservice.data.pojo.request.commentEmotion.CommentEmotionFindAllRequest;
import edu.ou.activityqueryservice.data.pojo.response.commentEmotion.CommentEmotionFindAllResponse;
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
public class CommentEmotionFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<CommentEmotionDocument>> commentEmotionFindAllRepository;
    private final IBaseRepository<Query, Integer> commentEmotionGetPageAmountRepository;
    private final IBaseRepository<Integer, EmotionDocument> emotionFindByIdRepository;
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
        if (validValidation.isInValid(request, CommentEmotionFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "comment emotion parameters"
            );
        }
    }

    /**
     * Find all comment emotion with pagination
     *
     * @param request request
     * @return comment emotion list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final CommentEmotionFindAllRequest commentEmotionFindAllRequest =
                (CommentEmotionFindAllRequest) request;

        final Query query = this.filter(commentEmotionFindAllRequest);
        final List<CommentEmotionDocument> commentEmotionDocuments = commentEmotionFindAllRepository.execute(query);
        final int pageAmount = commentEmotionGetPageAmountRepository.execute(query.skip(0).limit(0));

        final List<CommentEmotionFindAllResponse> commentEmotionFindAllResponses = new ArrayList<>();
        commentEmotionDocuments.forEach(commentEmotionDocument -> {
            final Object userInfo = rabbitTemplate.convertSendAndReceive(
                    UserFindDetailByIdQueueE.EXCHANGE,
                    UserFindDetailByIdQueueE.ROUTING_KEY,
                    commentEmotionDocument.getUserId()
            );
            final Map<String, Object> userInfoData = (Map<String, Object>) userInfo;
            final EmotionDocument emotionDocument =
                    emotionFindByIdRepository.execute(commentEmotionDocument.getEmotionId());

            commentEmotionFindAllResponses.add(
                    new CommentEmotionFindAllResponse()
                            .setIcon(emotionDocument.getIcon())
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
                                "data", commentEmotionFindAllResponses,
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
    private Query filter(CommentEmotionFindAllRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getCommentId())) {
            query.addCriteria(
                    Criteria.where("commentId")
                            .is(request.getCommentId())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
