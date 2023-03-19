package edu.ou.activityqueryservice.service.feedBackType;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.FeedBackTypeDocument;
import edu.ou.activityqueryservice.data.pojo.request.feedBackType.FeedBackTypeFindDetailRequest;
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
public class FeedBackTypeFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, FeedBackTypeDocument> feedBackTypeFindBySlugRepository;
    private final ValidValidation validValidation;

    /**
     * validate feedback type detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, FeedBackTypeFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "feedback type parameters"
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
        final FeedBackTypeFindDetailRequest feedBackTypeFindDetailRequest = (FeedBackTypeFindDetailRequest) request;

        final FeedBackTypeDocument feedBackTypeDocument = feedBackTypeFindBySlugRepository
                .execute(feedBackTypeFindDetailRequest.getFeedBackTypeSlug());

        if (Objects.isNull(feedBackTypeDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "feedback type",
                    "feedback type slug",
                    feedBackTypeFindDetailRequest.getFeedBackTypeSlug()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        feedBackTypeDocument,
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
