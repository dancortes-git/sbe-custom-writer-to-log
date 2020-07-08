package br.com.dcc.springbatchexamples.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.dcc.springbatchexamples.listener.SimpleChunkListener;
import br.com.dcc.springbatchexamples.listener.SimpleJobListener;
import br.com.dcc.springbatchexamples.writer.LogStringItemWriter;

@Configuration
public class CustomWriterToLogConfiguration {

	@Bean
	public ItemReader<String> customWriterToLogReader() {
		List<String> items = new ArrayList<>(100);

		for (int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));

		}

		return new ListItemReader<>(items);
	}

	@Bean
	public LogStringItemWriter customWriterToLogWriter() {
		return new LogStringItemWriter();
	}

	@Bean
	public Step customWriterToLogStep1(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("CustomWriterToLogStep1")
				.<String, String>chunk(10)
				.listener(new SimpleChunkListener())
				.reader(customWriterToLogReader())
				.writer(customWriterToLogWriter())
				.build();
	}

	@Bean
	public Job customWriterToLogJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		return jobBuilderFactory.get("CustomWriterToLogJob")
				.start(customWriterToLogStep1(stepBuilderFactory))
				.listener(new SimpleJobListener())
				.build();

	}

}
