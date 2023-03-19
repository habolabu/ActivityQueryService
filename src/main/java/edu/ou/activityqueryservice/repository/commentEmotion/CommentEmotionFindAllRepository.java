package edu.ou.activityqueryservice.repository.commentEmotion;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.CommentEmotionDocument;
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
public class CommentEmotionFindAllRepository extends BaseRepository<Query, List<CommentEmotionDocument>> {
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
                    "comment emotion find all query"
            );
        }
    }

    /**
     * Find all comment
     *
     * @param query query
     * @return list of comment
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<CommentEmotionDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                CommentEmotionDocument.class
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }

}
