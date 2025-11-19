package com.TeamMaker.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNickname is a Querydsl query type for Nickname
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNickname extends EntityPathBase<Nickname> {

    private static final long serialVersionUID = -1304624638L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNickname nickname1 = new QNickname("nickname1");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath nickname = createString("nickname");

    public final QStreamer streamer;

    public final EnumPath<Tier> tier = createEnum("tier", Tier.class);

    public QNickname(String variable) {
        this(Nickname.class, forVariable(variable), INITS);
    }

    public QNickname(Path<? extends Nickname> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNickname(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNickname(PathMetadata metadata, PathInits inits) {
        this(Nickname.class, metadata, inits);
    }

    public QNickname(Class<? extends Nickname> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.streamer = inits.isInitialized("streamer") ? new QStreamer(forProperty("streamer")) : null;
    }

}

