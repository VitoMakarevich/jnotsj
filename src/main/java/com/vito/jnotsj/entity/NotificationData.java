package com.vito.jnotsj.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class NotificationData {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column
        private LocalDateTime startDate;
        @Column
        private LocalDateTime endDate;
        @Column
        private String text;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id")
        private User author;
}