package com.TeamMaker.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStreamer is a Querydsl query type for Streamer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStreamer extends EntityPathBase<Streamer> {

    private static final long serialVersionUID = 415618497L;

    public static final QStreamer streamer = new QStreamer("streamer");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final ListPath<Nickname, QNickname> nicknames = this.<Nickname, QNickname>createList("nicknames", Nickname.class, QNickname.class, PathInits.DIRECT2);

    public final EnumPath<Position> position = createEnum("position", Position.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath streamerName = createString("streamerName");

    public final ListPath<TeamBoard, QTeamBoard> teamBoards = this.<TeamBoard, QTeamBoard>createList("teamBoards", TeamBoard.class, QTeamBoard.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QStreamer(String variable) {
        super(Streamer.class, forVariable(variable));
    }

    public QStreamer(Path<? extends Streamer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStreamer(PathMetadata metadata) {
        super(Streamer.class, metadata);
    }

}

