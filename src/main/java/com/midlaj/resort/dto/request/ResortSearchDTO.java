package com.midlaj.resort.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResortSearchDTO {

    private String place;

    private Long facilities[];

    private Long categories[];
}
