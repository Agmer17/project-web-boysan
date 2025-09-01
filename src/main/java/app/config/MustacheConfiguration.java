package app.config;

import com.samskivert.mustache.Mustache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MustacheConfiguration {
    @Bean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader loader) {
        return Mustache.compiler()
                .nullValue("") // kalau null -> jadi string kosong
                .defaultValue("") // kalau field nggak ada -> jadi string kosong
                .withLoader(loader);
    }
}
