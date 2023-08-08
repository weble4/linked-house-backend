package com.weble.linkedhouse.customer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerProfile is a Querydsl query type for CustomerProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerProfile extends EntityPathBase<CustomerProfile> {

    private static final long serialVersionUID = 1509413563L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerProfile customerProfile = new QCustomerProfile("customerProfile");

    public final com.weble.linkedhouse.util.QAuditingFields _super = new com.weble.linkedhouse.util.QAuditingFields(this);

    public final StringPath birthDate = createString("birthDate");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QCustomer customer;

    public final StringPath gender = createString("gender");

    public final StringPath nickname = createString("nickname");

    public final StringPath phoneNum = createString("phoneNum");

    public final NumberPath<Long> profileId = createNumber("profileId", Long.class);

    public final EnumPath<com.weble.linkedhouse.customer.entity.constant.PublicAt> publicAt = createEnum("publicAt", com.weble.linkedhouse.customer.entity.constant.PublicAt.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCustomerProfile(String variable) {
        this(CustomerProfile.class, forVariable(variable), INITS);
    }

    public QCustomerProfile(Path<? extends CustomerProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerProfile(PathMetadata metadata, PathInits inits) {
        this(CustomerProfile.class, metadata, inits);
    }

    public QCustomerProfile(Class<? extends CustomerProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

