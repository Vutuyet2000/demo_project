package com.rsupport.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeReq {
    private UUID id;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Content cannot be null")
    private String content;
    private String active;
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Invalid date format. The expected format is dd-MM-yyyy")
    private String startDate;
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Invalid date format. The expected format is dd-MM-yyyy")
    private String endDate;
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Invalid date format. The expected format is dd-MM-yyyy")
    private String registrationDate;
    private String author;
}
