package com.example.bookManagement.config;

import com.example.bookManagement.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PeopleProcessor implements ItemProcessor<People, People> {

    @Override
    public People process(People item) throws Exception {
        item.setFirstName(item.getFirstName()
                              .toUpperCase());
        item.setLastName(item.getLastName()
                             .toUpperCase());
        return item;
    }
}
