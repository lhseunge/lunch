package com.fun.lunch.domain.restaurant.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalKey {
    
    private String key;
    
    private PersonalKey(String key) {
        validateKey(key);
        this.key = key.trim();
    }
    
    public static PersonalKey of(String key) {
        return new PersonalKey(key);
    }
    
    private void validateKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("개인 키는 필수입니다.");
        }
        if (key.trim().length() > 100) {
            throw new IllegalArgumentException("개인 키는 100자를 초과할 수 없습니다.");
        }
    }
    
    public boolean isSameKey(PersonalKey other) {
        return Objects.equals(this.key, other.key);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PersonalKey that = (PersonalKey) obj;
        return Objects.equals(key, that.key);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
    
    @Override
    public String toString() {
        return key;
    }
}