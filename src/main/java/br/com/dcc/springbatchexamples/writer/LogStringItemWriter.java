package br.com.dcc.springbatchexamples.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogStringItemWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		log.info("The size of the chunk was: {}", items.size());
		for (String item : items) {
			log.info(">> item: {}", item);
		}
	}

}
