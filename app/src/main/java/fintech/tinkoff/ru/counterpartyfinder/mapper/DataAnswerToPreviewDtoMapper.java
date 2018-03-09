package fintech.tinkoff.ru.counterpartyfinder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.model.DataAnswer;

/**
 * 08.03.2018.
 */
@Mapper
public interface DataAnswerToPreviewDtoMapper {

    public DataAnswerToPreviewDtoMapper INSTANCE = Mappers.getMapper(DataAnswerToPreviewDtoMapper.class);

    @Mappings({
            @Mapping(target = "counterpartyName", source = "value"),
            @Mapping(target = "inn", source = "data.inn"),
            @Mapping(target = "address", source = "data.address.value")
    })
    PreviewDto map(DataAnswer answer);

    List<PreviewDto> map(List<DataAnswer> answers);
}
