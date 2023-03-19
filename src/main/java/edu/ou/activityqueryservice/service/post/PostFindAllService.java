package edu.ou.activityqueryservice.service.post;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.PostDocument;
import edu.ou.activityqueryservice.data.pojo.request.post.PostFindAllRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<PostDocument>> postFindAllRepository;
    private final IBaseRepository<Query, Integer> postGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, PostFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "post parameters"
            );
        }
    }

    /**
     * Find all post
     *
     * @param request request
     * @return feedback list
     * v
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final PostFindAllRequest postFindAllRequest = (PostFindAllRequest) request;

        final Query query = this.filter(postFindAllRequest);

        final List<PostDocument> postDocuments = postFindAllRepository.execute(query);
        final int pageAmount = postGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", postDocuments,
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
    private Query filter(PostFindAllRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getTitle())) {
            query.addCriteria(
                    Criteria.where("title")
                            .regex(
                                    request.getTitle(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getBTotalEmotion())
                && Objects.nonNull(request.getETotalEmotion())) {
            query.addCriteria(
                    Criteria.where("totalEmotion")
                            .gte(request.getBTotalEmotion())
                            .lte(request.getETotalEmotion())
            );
        }

        if (Objects.nonNull(request.getBTotalComment())
                && Objects.nonNull(request.getETotalComment())) {
            query.addCriteria(
                    Criteria.where("totalComment")
                            .gte(request.getBTotalComment())
                            .lte(request.getETotalComment())
            );
        }

        if (Objects.nonNull(request.getBCreatedAt())
                && Objects.nonNull(request.getECreatedAt())) {
            query.addCriteria(
                    Criteria.where("createdAt")
                            .gte(new Timestamp(request.getBCreatedAt().getTime()))
                            .lte(new Timestamp(request.getECreatedAt().getTime()))

            );
        }

        if(Objects.nonNull(request.getIsEdited())){
            query.addCriteria(
                    Criteria.where("isEdited")
                            .is(request.getIsEdited())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
