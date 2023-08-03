package com.midlaj.resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDTO {

    private Long id;

    private String originalImageLink;

    private String resizedImageLink;
}
