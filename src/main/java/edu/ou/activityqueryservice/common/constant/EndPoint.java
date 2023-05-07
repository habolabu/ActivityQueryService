package edu.ou.activityqueryservice.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndPoint {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Comment {
        public static final String BASE = "/comment";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Emotion {
        public static final String BASE = "/emotion";
        public static final String DETAIL = "/{emotionId}";
        public static final String ALL = "/all";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class FeedBack {
        public static final String BASE = "/feedback";
        public static final String DETAIL = "/{feedBackSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class FeedBackType {
        public static final String BASE = "/feedback-type";
        public static final String DETAIL = "/{feedBackTypeSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Post {
        public static final String BASE = "/post";
        public static final String DETAIL = "/{postSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CommentEmotion {
        public static final String BASE = "/comment-emotion";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class PostEmotion {
        public static final String BASE = "/post-emotion";
    }
}
