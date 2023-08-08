package com.weble.linkedhouse.host.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHost is a Querydsl query type for Host
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHost extends EntityPathBase<Host> {

    private static final long serialVersionUID = -1647883742L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHost host = new QHost("host");

    public final EnumPath<com.weble.linkedhouse.host.entity.constant.ApproveState> approvestate = createEnum("approvestate", com.weble.linkedhouse.host.entity.constant.ApproveState.class);

    public final com.weble.linkedhouse.customer.entity.QCustomer customer;

    public final NumberPath<Long> hostId = createNumber("hostId", Long.class);

    public final ListPath<com.weble.linkedhouse.house.entity.House, com.weble.linkedhouse.house.entity.QHouse> houses = this.<com.weble.linkedhouse.house.entity.House, com.weble.linkedhouse.house.entity.QHouse>createList("houses", com.weble.linkedhouse.house.entity.House.class, com.weble.linkedhouse.house.entity.QHouse.class, PathInits.DIRECT2);

    public final EnumPath<com.weble.linkedhouse.host.entity.constant.LockState> lockState = createEnum("lockState", com.weble.linkedhouse.host.entity.constant.LockState.class);

    public QHost(String variable) {
        this(Host.class, forVariable(variable), INITS);
    }

    public QHost(Path<? extends Host> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHost(PathMetadata metadata, PathInits inits) {
        this(Host.class, metadata, inits);
    }

    public QHost(Class<? extends Host> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

