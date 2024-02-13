package com.hourglass.order.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketGenerateRequest {
    @NotNull(message = "发车日期为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
}
