package com.gungor.ska.repo;

import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.entity.Kampanya;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@Tag("unit")
public class KampanyaRepoTest {

    @Autowired
    KampanyaRepo kampanyaRepo;

    Kampanya kampanya1;


    @BeforeEach
    void setup() {
        kampanya1 = new Kampanya();
        kampanya1.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanya1.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanya1.setKategori(KampanyaKategorisi.D);
        kampanya1.setDurum(KampanyaDurum.OnayBekliyor);
        kampanya1.setMukerrer(false);
    }

    @Test
    void givenKampanyawhenfindByKategoriAndIlanBaslikAndDetayAciklamasi_thenReturnKampanya(){
        //given
        kampanyaRepo.save(kampanya1);

        //when
        List<Kampanya> kampanyalarDB = kampanyaRepo.findByKategoriAndIlanBaslikAndDetayAciklamasi(KampanyaKategorisi.D,
                "Yolcu360 Ek İndirim Kampanyası",
                "Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(kampanyalarDB),
                () -> Assertions.assertNotEquals(0, kampanyalarDB.size()),
                () -> Assertions.assertEquals("Yolcu360 Ek İndirim Kampanyası", kampanyalarDB.get(0).getIlanBaslik()),
                () -> Assertions.assertEquals("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!", kampanyalarDB.get(0).getDetayAciklamasi()),
                () -> Assertions.assertEquals(KampanyaKategorisi.D, kampanyalarDB.get(0).getKategori()),
                () -> Assertions.assertEquals(KampanyaDurum.OnayBekliyor, kampanyalarDB.get(0).getDurum()),
                () -> Assertions.assertFalse(kampanyalarDB.get(0).isMukerrer())
        );

    }

    @Test
    void givenKampanyaSavedWhenCountAllByDurum_thenReturnCount() {
        //given
        kampanyaRepo.save(kampanya1);

        //when
        int countOnayBekliyor = kampanyaRepo.countAllByDurum(KampanyaDurum.OnayBekliyor);

        //then
        Assertions.assertEquals(1, countOnayBekliyor);
    }



}
