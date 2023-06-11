package com.gungor.ska.mapper;

import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.entity.Kampanya;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING)
public interface KampanyaMapper {

    @Mapping(source = "kampanyaId", target = "id")
    @Mapping(target = "kategori", ignore = true)
    Kampanya kampanyaDTOToKampanya(KampanyaDTO kampanyaDTO);

    @Mapping(source = "id", target = "kampanyaId")
    KampanyaDTO kampanyaToKampanyaDTO(Kampanya kampanya);

    KampanyaDTO kampanyaRequestDTOtoKampanyaDTO(KampanyaRequestDTO kampanyaRequestDTO);
}
