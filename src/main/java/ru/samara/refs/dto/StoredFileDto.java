package ru.samara.refs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredFileDto {

    private String guid;
    private String name;
    private String description;
    private String fileType;
    private byte[] content; 
}
