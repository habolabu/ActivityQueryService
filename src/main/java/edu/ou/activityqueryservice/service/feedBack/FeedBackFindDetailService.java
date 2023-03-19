package edu.ou.activityqueryservice.service.feedBack;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.FeedBackDocument;
import edu.ou.activityqueryservice.data.pojo.request.feedBack.FeedBackFindDetailRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, FeedBackDocument> feedBackFindBySlugRepository;
    private final ValidValidation validValidation;

    /**
     * validate feedback detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, FeedBackFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "feedback parameters"
            );
        }
    }

    /**
     * Find feedback detail
     *
     * @param request request
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final FeedBackFindDetailRequest feedBackFindDetailRequest = (FeedBackFindDetailRequest) request;

        final FeedBackDocument feedBackDocument = feedBackFindBySlugRepository
                .execute(feedBackFindDetailRequest.getFeedBackSlug());

        if (Objects.isNull(feedBackDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "feedback",
                    "feedback slug",
                    feedBackFindDetailRequest.getFeedBackSlug()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        feedBackDocument,
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest request) {
        // do nothing
    }
}
