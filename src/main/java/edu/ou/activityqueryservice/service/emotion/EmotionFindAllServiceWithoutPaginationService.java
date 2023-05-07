package edu.ou.activityqueryservice.service.emotion;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.EmotionDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionFindAllServiceWithoutPaginationService  extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<EmotionDocument>> emotionFindAllRepository;

    @Override
    protected void preExecute(IBaseRequest input) {
        // do nothing
    }

    @Override
    protected IBaseResponse doExecute(IBaseRequest input) {
        return new SuccessResponse<>(
                new SuccessPojo<>(
                        emotionFindAllRepository.execute(new Query()),
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }
}
