package com.midlaj.resort.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResortLocationDetailsDTO {

    private Long resortId;

    private String latitude;

    private String longitude;

    private String location;

}
