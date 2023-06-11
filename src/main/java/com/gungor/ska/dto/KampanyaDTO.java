package com.gungor.ska.dto;

import com.gungor.ska.enm.KampanyaDurum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KampanyaDTO {

    private String kampanyaId;

    private String ilanBaslik;

    private String detayAciklamasi;

    private String kategori;

    private String durum;

    private boolean isMukerrer;

    private List<String> changeLogs;
}
