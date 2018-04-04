package fintech.tinkoff.ru.counterpartyfinder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;

/**
 * 31.03.2018.
 */
@Mapper
public interface DataAnswerDtoToPreviewDtoMapper {
    public DataAnswerDtoToPreviewDtoMapper INSTANCE = Mappers.getMapper(DataAnswerDtoToPreviewDtoMapper.class);

    @Mappings({
            @Mapping(target = "hid", source = "hid"),
            @Mapping(target = "counterpartyName", source = "value"),
            @Mapping(target = "inn", source = "inn"),
            @Mapping(target = "address", source = "addressValue"),
            @Mapping(target = "isFavorite", source = "isFavorite")
    })
    PreviewDto map(DataAnswerDto answer);

    List<PreviewDto> map(List<DataAnswerDto> answers);
}
