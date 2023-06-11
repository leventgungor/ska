package com.gungor.ska.entity;

import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Kampanya {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String ilanBaslik;

    @Column(nullable = false)
    private String detayAciklamasi;

    @Column(nullable = false)
    private KampanyaKategorisi kategori;

    private boolean isMukerrer;

    @Column(nullable = false)
    private KampanyaDurum durum;

    @ElementCollection
    private List<String> changeLogs = new ArrayList<>();

    public void addChangelog(String changeLog){
        changeLogs.add(changeLog);
    }

}
