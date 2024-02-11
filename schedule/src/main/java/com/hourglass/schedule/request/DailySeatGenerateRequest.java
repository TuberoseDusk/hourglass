package com.hourglass.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailySeatGenerateRequest {
    private String trainCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
}
