package com.weble.linkedhouse.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -27478918L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final StringPath content = createString("content");

    public final com.weble.linkedhouse.review.entity.QFeedbackCustomer feedbackCustomer;

    public final com.weble.linkedhouse.review.entity.QFeedbackHost feedbackHost;

    public final StringPath reportedCustomer = createString("reportedCustomer");

    public final StringPath reporter = createString("reporter");

    public final NumberPath<Long> reportId = createNumber("reportId", Long.class);

    public final StringPath reportType = createString("reportType");

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feedbackCustomer = inits.isInitialized("feedbackCustomer") ? new com.weble.linkedhouse.review.entity.QFeedbackCustomer(forProperty("feedbackCustomer"), inits.get("feedbackCustomer")) : null;
        this.feedbackHost = inits.isInitialized("feedbackHost") ? new com.weble.linkedhouse.review.entity.QFeedbackHost(forProperty("feedbackHost"), inits.get("feedbackHost")) : null;
    }

}

