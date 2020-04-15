package com.endava.bootifuljms.weather.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@Entity
public class Report {

    @Id
    @SequenceGenerator(name = "report_sequence_generator", sequenceName = "report_sequence", allocationSize = 1)
    @GeneratedValue(generator = "report_sequence_generator")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

    @JsonIgnore
//    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;
}
