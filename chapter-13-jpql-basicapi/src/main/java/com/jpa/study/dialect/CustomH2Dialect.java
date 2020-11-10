package com.jpa.study.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomH2Dialect extends H2Dialect {
    public CustomH2Dialect() {
        //사용할 함수명, DB에 구현된 함수명, 반환타입
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
