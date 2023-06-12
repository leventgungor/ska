package com.gungor.ska.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KampanyaRequestDTO {

    @Size(min = 10, max = 50, message = "İlan başlığı 10 ve 50 karakter arasında olmalıdır.")
    @Pattern(regexp = "^[a-zA-Z0-9].*", message = "İlan başlığı harf veya rakam ile başlamalıdır.")
    @Schema(description = "İlan Başlığı", example = "MGS Güvenlik'ten %50 İndirim")
    private String ilanBaslik;

    @Size(min = 20, max = 200, message = "Detay Açıklaması 20 ve 200 karakter arasında olmalıdır.")
    @Schema(description = "Detay Açıklaması", example = "İşyerim Sigortası ve/veya Güvenli İşyerim Sigortası’nda vadesi devam eden müşterilerimiz için MGS Güvenlik’ten %50 indirim fırsatı sunuyoruz.  ")
    private String detayAciklamasi;

    @Schema(description = "Kampanya Kategorisi", example = "Tamamlayıcı Sağlık Sigortası")
    private String kategori;
}
