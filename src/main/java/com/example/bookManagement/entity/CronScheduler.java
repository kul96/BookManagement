package com.example.bookManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CronScheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String taskName;
    private String cronExpression;
    private String status;

    @PrePersist
    private void setValue() {
        this.status = "pending";
    }

}
