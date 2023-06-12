package com.gungor.ska.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KampanyaListesiDTO {
    private int aktifKampanyaSayisi;
    private int deAktifKampanyaSayisi;
    private int onayBekleyenKampanyaSayisi;
    private List<KampanyaDTO> kampanyaList;
}
