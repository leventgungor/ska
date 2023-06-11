package com.gungor.ska.dto;

import com.gungor.ska.entity.Kampanya;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class KampanyaListesiResponseDTO {
    private int aktifKampanyaSayisi;
    private int deAktifKampanyaSayisi;
    private int onayBekleyenKampanyaSayisi;
    private List<Kampanya> kampanyaList;
}
