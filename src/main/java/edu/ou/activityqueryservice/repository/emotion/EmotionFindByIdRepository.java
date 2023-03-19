package edu.ou.activityqueryservice.repository.emotion;

import edu.ou.activityqueryservice.common.constant.CodeStatus;
import edu.ou.activityqueryservice.data.entity.EmotionDocument;
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
public class EmotionFindByIdRepository extends BaseRepository<Integer, EmotionDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate emotion id
     *
     * @param emotionId emotion id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer emotionId) {
        if (validValidation.isInValid(emotionId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "emotion id"
            );
        }
    }

    /**
     * Find emotion detail
     *
     * @param emotionId emotion id
     * @return emotion detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected EmotionDocument doExecute(Integer emotionId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("oId")
                                .is(emotionId)
                ),
                EmotionDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
