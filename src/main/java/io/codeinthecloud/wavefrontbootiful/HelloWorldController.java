package io.codeinthecloud.wavefrontbootiful;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class HelloWorldController {

    private MeterRegistry meterRegistry;

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    public HelloWorldController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/")
    public String main(Model model) {
        return "helloworld";
    }

    @Scheduled(fixedRate = 5000)
    @Timed(description = "Time spent serving orders")
    public void generateRandom() throws InterruptedException {
        Random random = new Random();
        int rvalue = random.nextInt(1000);
        Gauge.builder("wavefrontbootiful.randomValue", rvalue, Integer::intValue)
                .description("Latest Random Value Generated")
                .register(meterRegistry);
        Thread.sleep(rvalue);
    }

}