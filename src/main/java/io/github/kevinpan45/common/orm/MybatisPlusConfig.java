package io.github.kevinpan45.common.orm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import com.baomidou.mybatisplus.extension.ddl.IDdl;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List<IDdl> ddlList) {
        return new DdlApplicationRunner(ddlList);
    }
}
