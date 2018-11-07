package com.blockchain.commune;

/**
 * Created by wenfengzhang on 18/3/23.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /** * SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/resources/webjars/映射 * 所以该方法不需要重写，如果在SpringMVC中，可能需要重写定义（我没有尝试） * 重写该方法需要 extends WebMvcConfigurerAdapter * */

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了 * （访问页面就可以看到效果了） *
     */
    @Bean
    public Docket ewalletApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("communeapi")
                .genericModelSubstitutes(DeferredResult.class)
// .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/.*")))//过滤的接口
                .build()
                .apiInfo(ewalletApiInfo());
    }


    private ApiInfo ewalletApiInfo() {
        return new ApiInfoBuilder().
                title("区块链公社api").
                description("api 接口").
                version("1.0").
                termsOfServiceUrl("NO terms of service").
                license("区块链项目使用").
                licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").build();

    }




}