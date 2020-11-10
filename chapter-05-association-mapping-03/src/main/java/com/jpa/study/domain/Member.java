package com.jpa.study.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 1대1 매핑 예제
 *
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"LOCKER_ID"}, name = "MEMBER_UK")})
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    
    private String userName;
    
    @OneToOne //일대일 이므로 FK에 유니크 제약조건을 추가한다(객체에 설정될 것이 아니면 DB에 이미 걸려있야한다.)
    @JoinColumn(name = "LOCKER_ID", foreignKey = @ForeignKey(name="MEMBER_FK"))
    Locker locker;

    public Member() { }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}
