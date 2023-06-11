package com.gungor.ska.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class KampanyaDurumRequestDTO {

    @NotEmpty
    @Schema(description = "Kampanya ID", example = "1")
    private Long kampanyaID;

}
