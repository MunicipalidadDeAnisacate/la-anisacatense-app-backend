package sistemaDeAlumbrado.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

// Esta clase solo habilita las tareas programadas
@Configuration
@EnableScheduling
public class SchedulingConfig {
}
