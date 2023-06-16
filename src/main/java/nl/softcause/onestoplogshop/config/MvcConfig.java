package nl.softcause.onestoplogshop.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/index.html");
        registry.addRedirectViewController("/notFound","/404.html");
        registry.addRedirectViewController("/notAuthorized","/401.html");
        registry.addRedirectViewController("/forbidden","/403.html");
        registry.addRedirectViewController("/error","/50x.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("/", "classpath:/static/").setCachePeriod(-1);
        registry.addResourceHandler("/**").addResourceLocations("/", "file:/Users/renzoslijp/Documents/workspace/softcause/onestoplogshop/src/main/resources/static/").setCachePeriod(-1);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notFound"));
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/notAuthorized"));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/forbidden"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/notFound"));
        };
    }
}