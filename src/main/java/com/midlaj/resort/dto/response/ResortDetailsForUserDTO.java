package com.midlaj.resort.dto.response;

import com.midlaj.resort.entity.ResortCategory;
import com.midlaj.resort.entity.ResortFacility;
import com.midlaj.resort.entity.ResortImages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResortDetailsForUserDTO {

    private Set<ResortFacility> facilities;

    private ResortCategory category;

    private List<ImageResponseDTO> images;

    public void setDtoOfResortImages (Set<ResortImages> images) {
        List<ImageResponseDTO> resortImagesResponse = new ArrayList<>();
        for (ResortImages image: images) {
            resortImagesResponse.add(ImageResponseDTO.builder()
                    .originalImageLink(image.getOriginalImageLink())
                    .resizedImageLink(image.getResizedImageLink())
                    .id(image.getId())
                    .build());

        }

        this.images = resortImagesResponse;
    }
}
