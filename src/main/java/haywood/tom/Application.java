package haywood.tom;

import haywood.tom.application.AddressBookRepl;
import haywood.tom.application.CommandProcessor;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import java.util.Collection;

@SpringBootApplication
@Configuration
public class Application implements CommandLineRunner {

    private Collection<CommandProcessor> commandProcessors;

    public Application(Collection<CommandProcessor> commandProcessors) {
        this.commandProcessors = commandProcessors;
    }

    @Override
    public void run(String... args) {
        new AddressBookRepl(commandProcessors, System.in, System.out).run();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
