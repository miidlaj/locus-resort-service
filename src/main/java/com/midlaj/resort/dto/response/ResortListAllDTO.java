package com.midlaj.resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResortListAllDTO {

    private Long resortId;

    private Long userId;

    private String resortName;

    private Date createdTime;

    private Date updatedTime;

    private String createdStatus;

    private Boolean approved;

    private String defaultImageUrl;
}
