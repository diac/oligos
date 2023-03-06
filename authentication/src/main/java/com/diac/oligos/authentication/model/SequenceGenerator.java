package com.diac.oligos.authentication.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence_generator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceGenerator {

    @Id
    private int sequenceNumber;
    private String type;
}