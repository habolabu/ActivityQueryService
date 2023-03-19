package edu.ou.activityqueryservice.repository.post;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.PostDocument;
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
public class PostFindBySlugRepository extends BaseRepository<String, PostDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate post slug
     *
     * @param postSlug post slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String postSlug) {
        if (validValidation.isInValid(postSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "post slug"
            );
        }
    }

    /**
     * Find post detail
     *
     * @param postSlug post slug
     * @return post detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected PostDocument doExecute(String postSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(postSlug)
                ),
                PostDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
