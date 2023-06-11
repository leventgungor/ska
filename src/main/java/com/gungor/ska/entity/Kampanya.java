package com.gungor.ska.entity;

import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.enm.KampanyaDurum;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Kampanya {

    @Id
    @GeneratedValue
    private Long id;

    private String ilanBaslik;

    private String detayAciklamasi;

    private KampanyaKategorisi kategori;

    private boolean isMukerrer;

    private KampanyaDurum durum;

    @ElementCollection
    private List<String> changeLogs = new ArrayList<>();

    public void addChangelog(String changeLog){
        changeLogs.add(changeLog);
    }

}
