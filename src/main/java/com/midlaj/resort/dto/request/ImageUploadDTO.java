package com.midlaj.resort.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadDTO {

    @NotNull(message = "Image Cannot be null")
    private MultipartFile image;

    @NotNull(message = "User Id cannot be null")
    @Size(max = 1, message = "User Id is not valid")
    private Long resortId;
}
