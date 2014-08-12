package com.example.josip;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

//import javax.annotation.Generated;


/**
 * QExample is a Querydsl query type for Example
 */
//@Generated("com.mysema.query.codegen.EntitySerializer")
public class QExample extends EntityPathBase<Example> {

    private static final long serialVersionUID = -1718653024;

    public static final QExample example = new QExample("example");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public QExample(String variable) {
        super(Example.class, forVariable(variable));
    }

    public QExample(Path<? extends Example> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExample(PathMetadata<?> metadata) {
        super(Example.class, metadata);
    }

}

