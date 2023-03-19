package edu.ou.activityqueryservice.repository.feedBack;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.FeedBackDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedBackFindAllRepository extends BaseRepository<Query, List<FeedBackDocument>> {
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
                    "feedback find all query"
            );
        }
    }

    /**
     * Find all feedback
     *
     * @param query filter
     * @return list of feedback
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<FeedBackDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                FeedBackDocument.class
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }
}
