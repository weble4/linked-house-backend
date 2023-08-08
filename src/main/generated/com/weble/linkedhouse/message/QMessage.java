package com.weble.linkedhouse.message;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = 1753983467L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final StringPath content = createString("content");

    public final NumberPath<Long> messageId = createNumber("messageId", Long.class);

    public final NumberPath<Integer> messageRoom = createNumber("messageRoom", Integer.class);

    public final EnumPath<com.weble.linkedhouse.message.constant.ReadCheck> readCheck = createEnum("readCheck", com.weble.linkedhouse.message.constant.ReadCheck.class);

    public final DateTimePath<java.time.LocalDateTime> readTime = createDateTime("readTime", java.time.LocalDateTime.class);

    public final com.weble.linkedhouse.customer.entity.QCustomer recieveCustomer;

    public final com.weble.linkedhouse.customer.entity.QCustomer sendCustomer;

    public final DateTimePath<java.time.LocalDateTime> sendTime = createDateTime("sendTime", java.time.LocalDateTime.class);

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recieveCustomer = inits.isInitialized("recieveCustomer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("recieveCustomer"), inits.get("recieveCustomer")) : null;
        this.sendCustomer = inits.isInitialized("sendCustomer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("sendCustomer"), inits.get("sendCustomer")) : null;
    }

}

