package com.fun.lunch.domain.restaurant.domain;

import com.fun.lunch.domain.restaurant.domain.vo.PersonalKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "personal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Personal {
    
    @EmbeddedId
    private PersonalKey personalKey;
    
    private Personal(PersonalKey personalKey) {
        this.personalKey = personalKey;
    }
    
    public static Personal of(String key) {
        return new Personal(PersonalKey.of(key));
    }
    
    public static Personal of(PersonalKey personalKey) {
        return new Personal(personalKey);
    }
    
    public boolean isSamePerson(Personal other) {
        return this.personalKey.isSameKey(other.personalKey);
    }
    
    public String getKey() {
        return this.personalKey.getKey();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Personal personal = (Personal) obj;
        return Objects.equals(personalKey, personal.personalKey);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(personalKey);
    }
    
    @Override
    public String toString() {
        return String.format("Personal(key=%s)", personalKey.getKey());
    }
}