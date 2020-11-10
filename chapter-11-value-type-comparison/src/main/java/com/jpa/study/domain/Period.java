package com.jpa.study.domain;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;


/**
 * 값 비교를 위한 equals 메소드 재정의
 * equals 메소드 처리시 여기서는 필드에 직접 접근 보다는
 * getter메서드를 사용해서 접근해서 처리할 것 
 * 이유는 프록시 객체 때문이다.
 */
@Embeddable 
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public Period() {} 
    
    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEndDate() == null) ? 0 : endDate.hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : startDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Period other = (Period) obj;
        if (getEndDate() == null) {
            if (other.getEndDate() != null)
                return false;
        } else if (!getEndDate().equals(other.getEndDate()))
            return false;
        if (getStartDate() == null) {
            if (other.getStartDate() != null)
                return false;
        } else if (!getStartDate().equals(other.getStartDate()))
            return false;
        return true;
    }
}
