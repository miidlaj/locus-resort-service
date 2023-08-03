package com.midlaj.resort.dto.response;

import com.midlaj.resort.entity.ResortAddress;
import com.midlaj.resort.entity.ResortLocationDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResortSearchResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String defaultImageLink;

    private String category;

    private String[] facilities;

    private ResortAddress resortAddress;

    private ResortLocationDetails locationDetails;
}

