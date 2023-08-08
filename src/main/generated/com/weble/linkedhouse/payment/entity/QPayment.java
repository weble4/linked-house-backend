package com.weble.linkedhouse.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -1438958472L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final com.weble.linkedhouse.house.entity.QHouse house;

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> requestDay = createDateTime("requestDay", java.time.LocalDateTime.class);

    public final com.weble.linkedhouse.reservation.entity.QReservation reservation;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.house = inits.isInitialized("house") ? new com.weble.linkedhouse.house.entity.QHouse(forProperty("house"), inits.get("house")) : null;
        this.reservation = inits.isInitialized("reservation") ? new com.weble.linkedhouse.reservation.entity.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

