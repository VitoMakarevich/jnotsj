package com.vito.jnotsj.common.kafkaProcessing;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public abstract class UniqueMessage {
    @Id()
    @Type(type = "pg-uuid")
    private UUID id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private BaseKafkaMessage payload;
}
