package english.transcription.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("classpath:project.properties")
public class SpringConfig implements WebMvcConfigurer {
    public final Environment environment;

    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public String getStringOfSite () {
        return environment.getProperty("transcriptionURL");
    }
}
