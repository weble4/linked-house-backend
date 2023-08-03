package com.weble.linkedhouse.house.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weble.linkedhouse.house.entity.House;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseCustomRepositoryImpl implements HouseCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public HouseCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override // 추후 HouseDTO로 리팩토링 필요
    public List<House> findAllHouseFetchJoin(){
        return jpaQueryFactory.selectFrom()
                .innerJoin()
                .where()
                .fetch();
    }
}
