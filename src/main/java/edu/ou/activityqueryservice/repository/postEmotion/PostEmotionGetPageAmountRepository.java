package edu.ou.activityqueryservice.repository.postEmotion;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.PostEmotionDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostEmotionGetPageAmountRepository extends BaseRepository<Query, Integer> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param query input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Query query) {
        if (validValidation.isInValid(query)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "post emotion get page amount query"
            );
        }
    }

    /**
     * Get page amount
     *
     * @param query null
     * @return page amount
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected Integer doExecute(Query query) {
        return Math.toIntExact(
                mongoTemplate.count(
                        query,
                        PostEmotionDocument.class
                )
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }
}
