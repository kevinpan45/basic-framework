package io.github.kevinpan45.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@ComponentScan
@Slf4j
public class PackageScanner {
    public void initLog() {
        log.info("KP45 Tech Inc. MicroService Basic Framework Loaded");
    }
}
