package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@Jacksonized
public class ItemRequestDto {
    @NotBlank
    @Size(max = 500, message = "Максимальная длина сообщения -> 500 символов")
    private String description;
}
