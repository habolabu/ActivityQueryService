package edu.ou.activityqueryservice.repository.feedBack;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.FeedBackDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedBackFindBySlugRepository extends BaseRepository<String, FeedBackDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate feedback slug
     *
     * @param feedBackSlug feedback slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String feedBackSlug) {
        if (validValidation.isInValid(feedBackSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "feedback slug"
            );
        }
    }

    /**
     * Find feedback detail
     *
     * @param feedBackSlug feedback slug
     * @return feedback detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected FeedBackDocument doExecute(String feedBackSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(feedBackSlug)
                ),
                FeedBackDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
