package ru.samara.refs.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("files")
@Builder
public class StoredFile {

    @Id
    @Column("guid")
    private UUID guid;
    @Column("name")
    private String name;
    @Column("description")
    private String description;
    @Column("file_type")
    private String fileType;
    @Column("content")
    private byte[] content;
}
