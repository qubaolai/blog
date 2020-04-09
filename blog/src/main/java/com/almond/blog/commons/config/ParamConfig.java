package com.almond.blog.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParamConfig {
    @Value("size")
    public static Integer size;
}
