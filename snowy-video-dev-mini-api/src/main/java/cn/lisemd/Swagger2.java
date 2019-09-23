package cn.lisemd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * Swagger2 配置文件
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("cn.lisemd.controller"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * 构件api文档信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("Swagger2  Snowy小程序API文档")
                // 设置联系人
                .contact(new Contact("Lisemd", "http://www.lisemd.cn", "Lisemd1096@outlook.com"))
                // 描述
                .description("欢迎访问此接口文档")
                // 定义版本号
                .version("1.0")
                .build();
    }
}
