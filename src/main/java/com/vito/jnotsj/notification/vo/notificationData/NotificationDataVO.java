package com.vito.jnotsj.notification.vo.notificationData;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class NotificationDataVO {
    private int id;

    @Size(max = 300)
    @NotBlank
    private String text;

    @Future
    private Date startDate;

    @Future
    private Date endDate;
}
