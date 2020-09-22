package com.vito.jnotsj.notification.entity;

import com.vito.jnotsj.auth.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class NotificationData {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column
        private Date startDate;
        @Column
        private Date endDate;
        @Column
        private String text;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id")
        private User author;
}