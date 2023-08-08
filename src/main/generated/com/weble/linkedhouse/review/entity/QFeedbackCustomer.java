package com.weble.linkedhouse.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedbackCustomer is a Querydsl query type for FeedbackCustomer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedbackCustomer extends EntityPathBase<FeedbackCustomer> {

    private static final long serialVersionUID = -914722707L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedbackCustomer feedbackCustomer = new QFeedbackCustomer("feedbackCustomer");

    public final com.weble.linkedhouse.util.QAuditingFields _super = new com.weble.linkedhouse.util.QAuditingFields(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.weble.linkedhouse.customer.entity.QCustomer customer;

    public final NumberPath<Long> feedbackcustomerId = createNumber("feedbackcustomerId", Long.class);

    public final com.weble.linkedhouse.house.entity.QHouse house;

    public final NumberPath<Integer> scoreClean = createNumber("scoreClean", Integer.class);

    public final NumberPath<Integer> scoreCommunication = createNumber("scoreCommunication", Integer.class);

    public final NumberPath<Integer> scoreSatisfaction = createNumber("scoreSatisfaction", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QFeedbackCustomer(String variable) {
        this(FeedbackCustomer.class, forVariable(variable), INITS);
    }

    public QFeedbackCustomer(Path<? extends FeedbackCustomer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedbackCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedbackCustomer(PathMetadata metadata, PathInits inits) {
        this(FeedbackCustomer.class, metadata, inits);
    }

    public QFeedbackCustomer(Class<? extends FeedbackCustomer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.house = inits.isInitialized("house") ? new com.weble.linkedhouse.house.entity.QHouse(forProperty("house"), inits.get("house")) : null;
    }

}

