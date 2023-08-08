package com.weble.linkedhouse.customer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = 1681548046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomer customer = new QCustomer("customer");

    public final com.weble.linkedhouse.util.QAuditingFields _super = new com.weble.linkedhouse.util.QAuditingFields(this);

    public final EnumPath<com.weble.linkedhouse.customer.entity.constant.AuthState> authState = createEnum("authState", com.weble.linkedhouse.customer.entity.constant.AuthState.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath customerEmail = createString("customerEmail");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final QCustomerProfile customerProfile;

    public final StringPath customerPw = createString("customerPw");

    public final EnumPath<com.weble.linkedhouse.customer.entity.constant.DeleteRequest> deleteRequest = createEnum("deleteRequest", com.weble.linkedhouse.customer.entity.constant.DeleteRequest.class);

    public final EnumPath<com.weble.linkedhouse.customer.entity.constant.Role> role = createEnum("role", com.weble.linkedhouse.customer.entity.constant.Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCustomer(String variable) {
        this(Customer.class, forVariable(variable), INITS);
    }

    public QCustomer(Path<? extends Customer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomer(PathMetadata metadata, PathInits inits) {
        this(Customer.class, metadata, inits);
    }

    public QCustomer(Class<? extends Customer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerProfile = inits.isInitialized("customerProfile") ? new QCustomerProfile(forProperty("customerProfile"), inits.get("customerProfile")) : null;
    }

}

