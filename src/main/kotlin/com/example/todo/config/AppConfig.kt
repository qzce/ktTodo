package com.example.todo.config

import com.example.todo.database.TodoDataBase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration // 구동시 먼저 참조
class AppConfig {

    @Bean(initMethod = "init")
    fun todoDataBase(): TodoDataBase {

        return TodoDataBase()
    }

}