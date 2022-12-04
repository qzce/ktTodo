package com.example.todo.model.http

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.validation.FieldError
import javax.validation.Validation


class TodoDtoTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoDtoTest() {

        val todoDto = TodoDto().apply {
            this.title = "테스트"
            this.description = ""
            this.schedule = "2020-10-10 11:11:11"
        }

        val result = validator.validate(todoDto)

        Assertions.assertThat(result.isEmpty()).isEqualTo(true)
    }

}