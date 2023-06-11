package com.gungor.ska.repo;

import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.entity.Kampanya;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KampanyaRepo extends CrudRepository<Kampanya,Long> {

    List<Kampanya> findByKategoriAndIlanBaslikAndDetayAciklamasi(KampanyaKategorisi kategori, String baslik, String detayAciklamasi);

    int countAllByDurum(KampanyaDurum kampanyaDurum);
}
