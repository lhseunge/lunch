package com.fun.lunch.domain.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "personal")
@AllArgsConstructor
@NoArgsConstructor
public class Personal {

    @Id
    private String key;

    public static Personal of(String personalKey) {
        return new Personal(personalKey);
    }
}
