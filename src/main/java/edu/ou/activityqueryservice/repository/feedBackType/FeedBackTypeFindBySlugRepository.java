package edu.ou.activityqueryservice.repository.feedBackType;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.FeedBackTypeDocument;
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
public class FeedBackTypeFindBySlugRepository extends BaseRepository<String, FeedBackTypeDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate feedback type slug
     *
     * @param feedBackTypeSlug feedback type slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String feedBackTypeSlug) {
        if (validValidation.isInValid(feedBackTypeSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "feedback type slug"
            );
        }
    }

    /**
     * Find feedback type detail
     *
     * @param feedBackTypeSlug feedback type slug
     * @return feedback type detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected FeedBackTypeDocument doExecute(String feedBackTypeSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(feedBackTypeSlug)
                ),
                FeedBackTypeDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
