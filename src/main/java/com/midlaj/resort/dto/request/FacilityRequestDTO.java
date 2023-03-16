package com.midlaj.resort.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityRequestDTO {

    @NotNull(message = "The Facility name must not be empty")
    @Size(max = 40, message = "Facility name too long. Please enter maximum number of 50 characters.")
    @Size(min = 3, message = "Facility name too short. Please enter minimum number of 3 characters..")
    private String name;

    @NotNull(message = "The Facility description must not be empty")
    @Size(max = 150, message = "Facility description too long. Please enter maximum number of 50 characters.")
    @Size(min = 3, message = "Facility description too short. Please enter minimum number of 3 characters..")
    private String description;

    public String getName() {
        return name.toUpperCase();
    }
}
