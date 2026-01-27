package space.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///"+uploadPath+"/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 매핑 없는 URL -> /error
        // 단일 경로
        registry.addViewController("/{spring:[a-zA-Z0-9-]+}")
                .setViewName("forward:/error");

        // 2단 이상 경로
        registry.addViewController("/{spring:[a-zA-Z0-9-]+}/{path:[a-zA-Z0-9-]+}")
                .setViewName("forward:/error");

        registry.addViewController("/{spring:[a-zA-Z0-9-]+}/{path:[a-zA-Z0-9-]+}/{more:[a-zA-Z0-9-]+}")
                .setViewName("forward:/error");

    }
}
