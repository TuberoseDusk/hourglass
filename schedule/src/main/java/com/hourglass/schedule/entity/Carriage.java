package com.hourglass.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Carriage {
    private Integer carriageType;
    private Integer rowCount;
    private Integer columnCount;
    private String seatMark;
}
