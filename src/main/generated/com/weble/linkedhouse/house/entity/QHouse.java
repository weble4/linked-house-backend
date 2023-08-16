package com.weble.linkedhouse.house.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouse is a Querydsl query type for House
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouse extends EntityPathBase<House> {

    private static final long serialVersionUID = -1576418184L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHouse house = new QHouse("house");

    public final com.weble.linkedhouse.util.QAuditingFields _super = new com.weble.linkedhouse.util.QAuditingFields(this);

    public final EnumPath<com.weble.linkedhouse.house.entity.constant.AutoReservation> autoReservation = createEnum("autoReservation", com.weble.linkedhouse.house.entity.constant.AutoReservation.class);

    public final NumberPath<Integer> bathRoom = createNumber("bathRoom", Integer.class);

    public final NumberPath<Integer> bed = createNumber("bed", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.weble.linkedhouse.customer.entity.QCustomer customer;

    public final StringPath detailAddress = createString("detailAddress");

    public final ListPath<HouseImage, QHouseImage> imagePath = this.<HouseImage, QHouseImage>createList("imagePath", HouseImage.class, QHouseImage.class, PathInits.DIRECT2);

    public final StringPath location = createString("location");

    public final NumberPath<Integer> maxCapacity = createNumber("maxCapacity", Integer.class);

    public final NumberPath<Integer> minCapacity = createNumber("minCapacity", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> rentalId = createNumber("rentalId", Long.class);

    public final NumberPath<Integer> room = createNumber("room", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QHouse(String variable) {
        this(House.class, forVariable(variable), INITS);
    }

    public QHouse(Path<? extends House> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHouse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHouse(PathMetadata metadata, PathInits inits) {
        this(House.class, metadata, inits);
    }

    public QHouse(Class<? extends House> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.weble.linkedhouse.customer.entity.QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

