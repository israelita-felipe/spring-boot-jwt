package iadevelopment.jwt;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootJwtApplication implements WebMvcConfigurer {

	private static final long SECONDS = 1000;
	private static final long MINUTES = SECONDS * 60;
	private static final long HOURS = MINUTES * 60;

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setTaskExecutor(mvcTaskExecutor());
		configurer.setDefaultTimeout(HOURS / 2);
	}

	@Bean
	public AsyncTaskExecutor mvcTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(2);
		taskExecutor.setMaxPoolSize(50);
		taskExecutor.setQueueCapacity(50);
		taskExecutor.setThreadNamePrefix("rest-task-");
		return taskExecutor;
	}

	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringBootJwtApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
