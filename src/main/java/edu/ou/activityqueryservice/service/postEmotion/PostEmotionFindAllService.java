package edu.ou.activityqueryservice.service.postEmotion;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.EmotionDocument;
import edu.ou.activityqueryservice.data.entity.PostEmotionDocument;
import edu.ou.activityqueryservice.data.pojo.request.commentEmotion.CommentEmotionFindAllRequest;
import edu.ou.activityqueryservice.data.pojo.request.postEmotion.PostEmotionFindAllRequest;
import edu.ou.activityqueryservice.data.pojo.response.postEmotion.PostEmotionFindAllResponse;
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
public class PostEmotionFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<PostEmotionDocument>> postEmotionFindAllRepository;
    private final IBaseRepository<Query, Integer> postEmotionGetPageAmountRepository;
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
        if (validValidation.isInValid(request, PostEmotionFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "post emotion parameters"
            );
        }
    }

    /**
     * Find all post emotion with pagination
     *
     * @param request request
     * @return post emotion list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final PostEmotionFindAllRequest postEmotionFindAllRequest =
                (PostEmotionFindAllRequest) request;

        final Query query = this.filter(postEmotionFindAllRequest);
        final List<PostEmotionDocument> postEmotionDocuments = postEmotionFindAllRepository.execute(query);
        final int pageAmount = postEmotionGetPageAmountRepository.execute(query.skip(0).limit(0));

        final List<PostEmotionFindAllResponse> commentEmotionFindAllResponses = new ArrayList<>();
        postEmotionDocuments.forEach(postEmotionDocument -> {
            final Object userInfo = rabbitTemplate.convertSendAndReceive(
                    UserFindDetailByIdQueueE.EXCHANGE,
                    UserFindDetailByIdQueueE.ROUTING_KEY,
                    postEmotionDocument.getUserId()
            );
            final Map<String, Object> userInfoData = (Map<String, Object>) userInfo;
            final EmotionDocument emotionDocument =
                    emotionFindByIdRepository.execute(postEmotionDocument.getEmotionId());

            commentEmotionFindAllResponses.add(
                    new PostEmotionFindAllResponse()
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
    private Query filter(PostEmotionFindAllRequest request) {
        final Query query = new Query();

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
