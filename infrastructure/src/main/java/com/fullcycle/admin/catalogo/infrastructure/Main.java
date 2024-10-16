package com.fullcycle.admin.catalogo.infrastructure;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME,"dev");
        SpringApplication.run(WebServerConfig.class);

    }

    @Bean
    public ApplicationRunner runner(CategoryRepository repository){
        return  args -> {
            List<CategoryJpaEntity> categories = repository.findAll();
            var filmes = Category.newCategory("Filmes", null,true);
            repository.saveAndFlush(CategoryJpaEntity.from(filmes));
            repository.deleteAll();
        };
    }
}