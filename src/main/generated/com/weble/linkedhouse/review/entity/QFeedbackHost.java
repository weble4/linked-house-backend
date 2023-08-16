package com.weble.linkedhouse.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedbackHost is a Querydsl query type for FeedbackHost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedbackHost extends EntityPathBase<FeedbackHost> {

    private static final long serialVersionUID = 1711179191L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedbackHost feedbackHost = new QFeedbackHost("feedbackHost");

    public final com.weble.linkedhouse.util.QAuditingFields _super = new com.weble.linkedhouse.util.QAuditingFields(this);

    public final NumberPath<Integer> attitude = createNumber("attitude", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.weble.linkedhouse.customer.entity.QCustomer customer;

    public final NumberPath<Integer> damageDegree = createNumber("damageDegree", Integer.class);

    public final NumberPath<Long> feedbackHostId = createNumber("feedbackHostId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.weble.linkedhouse.customer.entity.QCustomer writer;

    public QFeedbackHost(String variable) {
        this(FeedbackHost.class, forVariable(variable), INITS);
    }

    public QFeedbackHost(Path<? extends FeedbackHost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedbackHost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedbackHost(PathMetadata metadata, PathInits inits) {
        this(FeedbackHost.class, metadata, inits);
    }

    public QFeedbackHost(Class<? extends FeedbackHost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.writer = inits.isInitialized("writer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("writer"), inits.get("writer")) : null;
    }

}

