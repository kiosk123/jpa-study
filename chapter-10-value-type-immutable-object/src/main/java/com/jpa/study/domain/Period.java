package com.jpa.study.domain;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable //임베디드 타입 정의 클래스에 설정
public class Period implements Cloneable {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public Period() {} //기본 생성자 필수
    
    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    private void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    private void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public Period clone() throws CloneNotSupportedException {
        Period clone = (Period)super.clone();
        clone.setStartDate(this.startDate);; //LocalDateTime immutable 타입임
        clone.setEndDate(this.endDate);
        return clone;
    }
}
