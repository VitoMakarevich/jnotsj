package com.vito.jnotsj.vo.notificationData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vito.jnotsj.entity.NotificationData;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private int authorId;
}
