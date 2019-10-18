package cn.lisemd;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 继承SpringBootServletInitializer，相当于使用web.xml的形式去启动部署
 */
public class WarStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        // 使用web.xml运行应用程序，指向Application，最后启动Springboot
        return builder.sources(Application.class);
    }
}
