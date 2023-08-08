package com.weble.linkedhouse.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCancelReservation is a Querydsl query type for CancelReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCancelReservation extends EntityPathBase<CancelReservation> {

    private static final long serialVersionUID = -164821154L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCancelReservation cancelReservation = new QCancelReservation("cancelReservation");

    public final NumberPath<Long> cancelId = createNumber("cancelId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> checkinDate = createDateTime("checkinDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> checkoutDate = createDateTime("checkoutDate", java.time.LocalDateTime.class);

    public final QReservation reservation;

    public final NumberPath<Integer> reservationNum = createNumber("reservationNum", Integer.class);

    public QCancelReservation(String variable) {
        this(CancelReservation.class, forVariable(variable), INITS);
    }

    public QCancelReservation(Path<? extends CancelReservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCancelReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCancelReservation(PathMetadata metadata, PathInits inits) {
        this(CancelReservation.class, metadata, inits);
    }

    public QCancelReservation(Class<? extends CancelReservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

