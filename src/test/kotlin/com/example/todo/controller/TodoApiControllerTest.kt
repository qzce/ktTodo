package com.example.todo.controller

import com.example.todo.controller.api.todo.TodoApiController
import com.example.todo.database.Todo
import com.example.todo.model.http.TodoDto
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
class TodoApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Order(1)
    @Test
    fun createTest() {

        val todoDto = TodoDto().apply {
            this.title = "테스트 일정"
            this.description = ""
            this.schedule = "2020-10-10 11:11:11"
        }

        val json = jacksonObjectMapper().writeValueAsString(todoDto)

        mockMvc.perform(
            post("/api/todo")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("\$.title").value("테스트 일정")
        ).andDo(print())
    }

    @Order(2)
    @Test
    fun updateTest() {

        val todoDto = TodoDto().apply {
            this.index = 1
            this.title = "테스트 일정 update"
            this.description = "test"
            this.schedule = "2020-10-10 11:11:11"
        }

        val json = jacksonObjectMapper().writeValueAsString(todoDto)

        mockMvc.perform(
            put("/api/todo")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("\$.title").value("테스트 일정 update")
        ).andDo(print())

    }


}