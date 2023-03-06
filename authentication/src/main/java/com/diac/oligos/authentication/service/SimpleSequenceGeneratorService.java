package com.diac.oligos.authentication.service;

import com.diac.oligos.authentication.model.SequenceGenerator;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SimpleSequenceGeneratorService implements SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    @Override
    public int generateSequence(String sequenceName) {
        SequenceGenerator sequenceGenerator = mongoOperations.findAndModify(
                Query.query(Criteria.where("type").is(sequenceName)),
                new Update().inc("sequenceNumber", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                SequenceGenerator.class
        );
        return !Objects.isNull(sequenceGenerator) ? sequenceGenerator.getSequenceNumber() : 1;
    }
}