package com.vito.jnotsj.vo.notificationData;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class NotificationDataVO {
    @Size(max = 300)
    @NotBlank
    private String text;

    @Future
    private LocalDateTime startDate;

    @Future
    private LocalDateTime endDate;
}
