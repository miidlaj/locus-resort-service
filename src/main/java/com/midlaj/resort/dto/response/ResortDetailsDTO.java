package com.midlaj.resort.dto.response;

import com.midlaj.resort.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResortDetailsDTO {

    private String name;

    private String description;

    private Long userId;

    private String defaultImageLink;

    private String resortCreateStatus;

    private Boolean enabled;

    private ResortCategory category;

    private Set<ResortFacility> facilities;

    private ResortAddress resortAddress;

    private List<ImageResponse> images;

    private Date createdTime;

    private Date updatedTime;

    private Boolean banned;

    private Boolean adminApproved;

    private ResortLocationDetails locationDetails;

    public void setDtoOfResortImages (Set<ResortImages> images) {
        List<ImageResponse> resortImagesResponse = new ArrayList<>();
        for (ResortImages image: images) {
            resortImagesResponse.add(ImageResponse.builder()
                    .originalImageLink(image.getOriginalImageLink())
                    .resizedImageLink(image.getResizedImageLink())
                    .id(image.getId())
                    .build());

        }

        this.images = resortImagesResponse;
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ImageResponse {

    private Long id;

    private String originalImageLink;

    private String resizedImageLink;
}

