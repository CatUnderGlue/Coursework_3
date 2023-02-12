package ru.catunderglue.socksstore.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.catunderglue.socksstore.models.enums.Color;
import ru.catunderglue.socksstore.models.enums.OperationType;
import ru.catunderglue.socksstore.models.enums.Size;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private OperationType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
    private int quantity;
    private Size size;
    private int cottonRel;
    private Color color;

    public Operation(OperationType type, int quantity, Size size, int cottonRel, Color color) {
        this.type = type;
        this.dateTime = LocalDateTime.now();
        this.quantity = quantity;
        this.size = size;
        this.cottonRel = cottonRel;
        this.color = color;
    }
}


