package io.github.kevinpan45.common;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
@Slf4j
public class PackageScanner {
    @PostConstruct
    public void initLog() {
        log.info("KP45 Tech Inc. MicroService Basic Framework Loaded");
    }
}
