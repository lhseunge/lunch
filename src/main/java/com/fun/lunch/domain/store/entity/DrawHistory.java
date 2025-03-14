package com.fun.lunch.domain.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Entity
@Table(name = "draw_history")
@NoArgsConstructor
@AllArgsConstructor
public class DrawHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Personal personal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @CreationTimestamp
    private Date date;

    public static DrawHistory of(Personal personal, Store store) {
        return new DrawHistory(personal, store);
    }

    public DrawHistory(Personal personal, Store store) {
        this.personal = personal;
        this.store = store;
    }
}
