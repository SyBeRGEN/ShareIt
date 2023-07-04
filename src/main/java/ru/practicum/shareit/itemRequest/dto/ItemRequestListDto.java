package ru.practicum.shareit.itemRequest.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestListDto {
    @JsonValue
    private List<ItemRequestDtoResponseWithItems> requests;
}
