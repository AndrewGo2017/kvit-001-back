package ru.sber.kvit001.model.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileRow {
    private Integer rowIndex;

    private List<String> rowData;

}
