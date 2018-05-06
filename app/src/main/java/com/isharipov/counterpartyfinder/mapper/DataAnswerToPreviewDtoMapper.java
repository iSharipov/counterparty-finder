package com.isharipov.counterpartyfinder.mapper;

import com.isharipov.counterpartyfinder.data.network.dto.PreviewDto;
import com.isharipov.counterpartyfinder.data.network.model.DataAnswer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 08.03.2018.
 */
@Mapper
public interface DataAnswerToPreviewDtoMapper {

    DataAnswerToPreviewDtoMapper INSTANCE = Mappers.getMapper(DataAnswerToPreviewDtoMapper.class);

    @Mappings({
            @Mapping(target = "hid", source = "data.hid"),
            @Mapping(target = "counterpartyName", source = "value"),
            @Mapping(target = "inn", source = "data.inn"),
            @Mapping(target = "address", source = "data.address.value"),
            @Mapping(target = "isFavorite", ignore = true)
    })
    PreviewDto map(DataAnswer answer);

    List<PreviewDto> map(List<DataAnswer> answers);
}
