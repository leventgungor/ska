package com.gungor.ska.enm;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum KampanyaKategorisi {

    TSS("Tamamlayıcı Sağlık Sigortası"),
    OSS("Özel Sağlık Sigortası"),
    HS("Hayat Sigortası"),
    D("Diğer");

    private final String isim;

    private static final Map<String, KampanyaKategorisi> isimler = Arrays.stream(KampanyaKategorisi.values()).collect(Collectors.toMap(KampanyaKategorisi::getIsim, Function.identity()));

    public static Optional<KampanyaKategorisi> isimdenKampanyaGetir(final String name) { return Optional.ofNullable(isimler.get(name));}

    KampanyaKategorisi(String isim) {
        this.isim = isim;
    }

    public KampanyaDurum kampanyaDurumBelirle(){
        return switch (this) {
            case TSS, OSS, D:
                yield  KampanyaDurum.OnayBekliyor;
            case HS:
                yield KampanyaDurum.Aktif;
        };
    }

}
