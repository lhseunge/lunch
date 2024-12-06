package com.fun.lunch.entity;

import jakarta.persistence.*;
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

}
