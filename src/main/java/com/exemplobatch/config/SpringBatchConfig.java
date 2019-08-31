package com.exemplobatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.exemplobatch.entity.User;

@Configuration
public class SpringBatchConfig {

	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		Step step = stepBuilderFactory.get("ETL-file-load")
	            .<User, User>chunk(100)
	            .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
		
		return jobBuilderFactory.get("ETL-Load")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<User> itemReader(@Value("$inputCSVFile") Resource resource){

	FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
	           flatFileItemReader.setResource(resource);
	           flatFileItemReader.setName("ItemReader");
	           flatFileItemReader.setLinesToSkip(1);
	           flatFileItemReader.setLineMapper(lineMapper());
	           flatFileItemReader.setStrict(false);
	           return flatFileItemReader;
	}
	
	 @Bean
	 public LineMapper<User> lineMapper() {
	  
	  DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
	  DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	  lineTokenizer.setDelimiter(",");
	  lineTokenizer.setStrict(false);
	  lineTokenizer.setNames(new String[] {"id","name","dept","salary"});
	  
	  BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	  fieldSetMapper.setTargetType(User.class);
	  defaultLineMapper.setLineTokenizer(lineTokenizer);
	  defaultLineMapper.setFieldSetMapper(fieldSetMapper);
	  
	  return defaultLineMapper;
	 }
}
