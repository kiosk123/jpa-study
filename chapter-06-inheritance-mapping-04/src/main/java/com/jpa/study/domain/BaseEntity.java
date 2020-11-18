package com.jpa.study.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

/**
 * DB상 상속관계는 아니지만
 * 객체상에서 필드가 반복될 경우 (공통 매핑 정보가 반복될때)
 * 반복되는 필드만 상위클래스로 빼는 방법
 *
 */
@MappedSuperclass //매핑정보만 받는 슈퍼클래스
public class BaseEntity {
    private String createBy;
    private LocalDateTime createTime;
    private String lastModified;
    private LocalDateTime lastModifiedDate;
}
