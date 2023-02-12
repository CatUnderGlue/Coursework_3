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
    Color color;
    Size size;
    @Min(1)
    @Max(100)
    int cottonRel;
    @Min(1)
    int quantity;
}
