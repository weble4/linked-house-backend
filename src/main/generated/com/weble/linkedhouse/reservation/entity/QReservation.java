package com.weble.linkedhouse.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -1698382024L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.weble.linkedhouse.util.QAuditingFields _super = new com.weble.linkedhouse.util.QAuditingFields(this);

    public final DateTimePath<java.time.LocalDateTime> checkinDate = createDateTime("checkinDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> checkoutDate = createDateTime("checkoutDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.weble.linkedhouse.customer.entity.QCustomer customer;

    public final com.weble.linkedhouse.house.entity.QHouse house;

    public final NumberPath<Long> reservationId = createNumber("reservationId", Long.class);

    public final NumberPath<Integer> reservationNum = createNumber("reservationNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.house = inits.isInitialized("house") ? new com.weble.linkedhouse.house.entity.QHouse(forProperty("house"), inits.get("house")) : null;
    }

}

