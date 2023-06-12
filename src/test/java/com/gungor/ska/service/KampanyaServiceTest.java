package com.gungor.ska.service;

import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.entity.Kampanya;
import com.gungor.ska.exception.BusinessException;
import com.gungor.ska.mapper.KampanyaMapper;
import com.gungor.ska.repo.KampanyaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class KampanyaServiceTest {

    @Mock
    private KampanyaRepo kampanyaRepo;

    @Mock
    private KampanyaMapper kampanyaMapper;

    @InjectMocks
    private KampanyaService kampanyaService;

    private Kampanya kampanya;
    private Kampanya kampanyaAktif;
    private Kampanya kampanyaDeAktif;
    private KampanyaDTO kampanyaDTO;
    private KampanyaDTO kampanyaDTOAktif;
    private KampanyaDTO kampanyaDTODeaktif;

    @BeforeEach
    void setup(){
        kampanyaDTO = new KampanyaDTO();
        kampanyaDTO.setKampanyaId(1L);
        kampanyaDTO.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanyaDTO.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanyaDTO.setKategori("Diğer");
        kampanyaDTO.setDurum("OnayBekliyor");
        kampanyaDTO.setMukerrer(false);

        kampanya = new Kampanya();
        kampanya.setId(1L);
        kampanya.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanya.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanya.setKategori(KampanyaKategorisi.D);
        kampanya.setDurum(KampanyaDurum.OnayBekliyor);
        kampanya.setMukerrer(false);

        kampanyaAktif = new Kampanya();
        kampanyaAktif.setId(1L);
        kampanyaAktif.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanyaAktif.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanyaAktif.setKategori(KampanyaKategorisi.D);
        kampanyaAktif.setDurum(KampanyaDurum.Aktif);
        kampanyaAktif.setMukerrer(false);

        kampanyaDTOAktif = new KampanyaDTO();
        kampanyaDTOAktif.setKampanyaId(1L);
        kampanyaDTOAktif.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanyaDTOAktif.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanyaDTOAktif.setKategori("Diğer");
        kampanyaDTOAktif.setDurum("Aktif");
        kampanyaDTOAktif.setMukerrer(false);

        kampanyaDeAktif = new Kampanya();
        kampanyaDeAktif.setId(1L);
        kampanyaDeAktif.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanyaDeAktif.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanyaDeAktif.setKategori(KampanyaKategorisi.D);
        kampanyaDeAktif.setDurum(KampanyaDurum.Deaktif);
        kampanyaDeAktif.setMukerrer(false);

        kampanyaDTODeaktif = new KampanyaDTO();
        kampanyaDTODeaktif.setKampanyaId(1L);
        kampanyaDTODeaktif.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanyaDTODeaktif.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanyaDTODeaktif.setKategori("Diğer");
        kampanyaDTODeaktif.setDurum("Deaktif");
        kampanyaDTODeaktif.setMukerrer(false);
    }

    @Test
    void givenKampanyaDTO_whenKampanyaOlustur_thenReturnKampanyaDTO(){
        //given
        given(kampanyaMapper.kampanyaDTOToKampanya(kampanyaDTO)).willReturn(kampanya);
        given(kampanyaRepo.save(kampanya)).willReturn(kampanya);
        given(kampanyaMapper.kampanyaToKampanyaDTO(kampanya)).willReturn(kampanyaDTO);

        //when
        KampanyaDTO result = kampanyaService.kampanyaOlustur(kampanyaDTO);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertEquals(kampanyaDTO.getIlanBaslik(), result.getIlanBaslik()),
                () -> Assertions.assertEquals(kampanyaDTO.getDetayAciklamasi(), result.getDetayAciklamasi()),
                () -> Assertions.assertEquals(kampanyaDTO.getKategori(), result.getKategori()),
                () -> Assertions.assertEquals(kampanyaDTO.getDurum(), result.getDurum()),
                () -> Assertions.assertEquals(kampanyaDTO.isMukerrer(), result.isMukerrer())
        );

        verify(kampanyaMapper, times(1)).kampanyaDTOToKampanya(kampanyaDTO);
        verify(kampanyaRepo, times(1)).save(kampanya);
        verify(kampanyaMapper, times(1)).kampanyaToKampanyaDTO(kampanya);
    }

    @Test
    void givenInvalidKategori_whenKampanyaOlustur_thenThrowBusinessException(){
        //given
        kampanyaDTO.setKategori("ABC");
        given(kampanyaMapper.kampanyaDTOToKampanya(kampanyaDTO)).willReturn(kampanya);

        //when
        Assertions.assertThrows(BusinessException.class, () -> kampanyaService.kampanyaOlustur(kampanyaDTO), "Geçerli Bir Kampanya Kategorisi Giriniz.");

        //then
        verify(kampanyaMapper, times(1)).kampanyaDTOToKampanya(kampanyaDTO);
    }

    @Test
    void givenKampanyaId_whenKampanyaAktiveEt_thenReturnKampanyaDTO(){
        //given
        given(kampanyaRepo.findById(1L)).willReturn(Optional.ofNullable(kampanya));
        given(kampanyaRepo.save(any())).willReturn(any());
        given(kampanyaMapper.kampanyaToKampanyaDTO(kampanyaAktif)).willReturn(kampanyaDTOAktif);

        //when
        KampanyaDTO result = kampanyaService.kampanyaAktiveEt(1L);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertEquals(kampanyaDTOAktif.getIlanBaslik(), result.getIlanBaslik()),
                () -> Assertions.assertEquals(kampanyaDTOAktif.getDetayAciklamasi(), result.getDetayAciklamasi()),
                () -> Assertions.assertEquals(kampanyaDTOAktif.getKategori(), result.getKategori()),
                () -> Assertions.assertEquals(kampanyaDTOAktif.getDurum(), result.getDurum()),
                () -> Assertions.assertEquals(kampanyaDTOAktif.isMukerrer(), result.isMukerrer())
        );

        verify(kampanyaRepo, times(1)).findById(1L);
        verify(kampanyaRepo, times(1)).save(any());
        verify(kampanyaMapper, times(1)).kampanyaToKampanyaDTO(any());
    }

    @Test
    void givenKampanyaId_whenKampanyaDeaktiveEt_thenReturnKampanyaDTO(){
        //given
        given(kampanyaRepo.findById(1L)).willReturn(Optional.ofNullable(kampanya));
        given(kampanyaRepo.save(any())).willReturn(kampanyaDeAktif);
        given(kampanyaMapper.kampanyaToKampanyaDTO(kampanyaDeAktif)).willReturn(kampanyaDTODeaktif);

        //when
        KampanyaDTO result = kampanyaService.kampanyaDeaktiveEt(1L);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertEquals(kampanyaDTODeaktif.getIlanBaslik(), result.getIlanBaslik()),
                () -> Assertions.assertEquals(kampanyaDTODeaktif.getDetayAciklamasi(), result.getDetayAciklamasi()),
                () -> Assertions.assertEquals(kampanyaDTODeaktif.getKategori(), result.getKategori()),
                () -> Assertions.assertEquals(kampanyaDTODeaktif.getDurum(), result.getDurum()),
                () -> Assertions.assertEquals(kampanyaDTODeaktif.isMukerrer(), result.isMukerrer())
        );

        verify(kampanyaRepo, times(1)).findById(1L);
        verify(kampanyaRepo, times(1)).save(any());
        verify(kampanyaMapper, times(1)).kampanyaToKampanyaDTO(any());
    }




}
