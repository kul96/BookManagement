package com.example.bookManagement.config;

import com.example.bookManagement.entity.People;
import com.example.bookManagement.repository.PeopleRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing   // if use this table not created ***
public class PeopleSpringBatchConfig {

    @Bean
    PeopleProcessor processor() {
        return new PeopleProcessor();
    }

    @Autowired
    private PeopleRepo peopleRepo;

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("import-people-job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("csv-step", jobRepository)
                .<People, People>chunk(10, platformTransactionManager)
                .reader(reader())
                .processor(processor()) //(optional)
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<People> reader() {
        return new FlatFileItemReaderBuilder<People>()
                .name("peopleItemReader")
                .resource(new ClassPathResource("people-1000.csv"))
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .targetType(People.class)
                .build();
    }

    public LineMapper<People> lineMapper() {
        /*
        [people.csv]  -->  FlatFileItemReader
                      |
                      v
                LineMapper<Person>
               /                 \
   LineTokenizer                 FieldSetMapper<Person>
 (DelimitedLineTokenizer)       (BeanWrapperFieldSetMapper)
     (splits line)               (maps fields to object)
        * */
        DefaultLineMapper<People> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // for csv file
//        lineTokenizer.setDelimiter(","); // because the default delimiter is already a comma
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("index", "userId", "firstName", "lastName", "gender", "email", "phone", "dateOfBirth",
                               "jobTitle"
        );

        BeanWrapperFieldSetMapper<People> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(People.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

//    @Bean
//    RepositoryItemWriter<People> writer() {
//        RepositoryItemWriter<People> itemWriter = new RepositoryItemWriter<>();
//        itemWriter.setMethodName("save"); // single people write
//        itemWriter.setRepository(peopleRepo);
//        return itemWriter;
//    }

    @Bean
    public ItemWriter<People> writer() {
        return chunk -> peopleRepo.saveAll(chunk); // list of people write
    }

}
