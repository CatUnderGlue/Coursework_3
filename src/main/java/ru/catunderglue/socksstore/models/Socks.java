package ru.catunderglue.socksstore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.catunderglue.socksstore.models.enums.Color;
import ru.catunderglue.socksstore.models.enums.Size;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socks {
    private Color color;
    private Size size;
    @Min(value = 1, message = "Процент содержания хлопка не может быть меньше 1")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private int cottonRel;
    @Min(value = 1, message = "Кол-во носков не может быть меньше 1")
    private int quantity;
}
