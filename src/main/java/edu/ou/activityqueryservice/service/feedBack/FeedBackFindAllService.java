package edu.ou.activityqueryservice.service.feedBack;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.FeedBackDocument;
import edu.ou.activityqueryservice.data.pojo.request.feedBack.FeedBackFindAllRequest;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<FeedBackDocument>> feedBackFindAllRepository;
    private final IBaseRepository<Query, Integer> feedBackGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, FeedBackFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "feedback parameters"
            );
        }
    }

    /**
     * Find all feedback
     *
     * @param request request
     * @return feedback list
     * v
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final FeedBackFindAllRequest feedBackFindAllRequest = (FeedBackFindAllRequest) request;

        final Query query = this.filter(feedBackFindAllRequest);

        final List<FeedBackDocument> feedBackDocuments = feedBackFindAllRepository.execute(query);
        final int pageAmount = feedBackGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", feedBackDocuments,
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
    private Query filter(FeedBackFindAllRequest request) {
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

        if (Objects.nonNull(request.getFeedBackTypeId())) {
            query.addCriteria(
                    Criteria.where("feedBackTypeId")
                            .is(request.getFeedBackTypeId())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
