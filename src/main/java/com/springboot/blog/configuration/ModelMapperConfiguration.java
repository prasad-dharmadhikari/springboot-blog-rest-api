package com.springboot.blog.configuration;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        PropertyMap<PostDto, Post> postDtoPostPropertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                skip(destination.getId());
//            }
//        };
//        modelMapper.addMappings(postDtoPostPropertyMap);
        return modelMapper;
    }
}
