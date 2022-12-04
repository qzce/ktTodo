package com.example.todo.repository

import com.example.todo.config.AppConfig
import com.example.todo.database.Todo
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @BeforeEach // 각 테스트 실행 되기전
    fun before() {
        todoRepositoryImpl.todoDataBase.init()
    }

    @Test
    fun saveTest() {

       val todo = Todo().apply {
           this.title = "테스트 일정"
           this.description = "테스트"
           this.schedule = LocalDateTime.now()
       }

        val result = todoRepositoryImpl.save(todo)

        Assertions.assertThat(result?.index)
            .isEqualTo(1)
        Assertions.assertThat(result?.createdAt)
            .isNotNull
        Assertions.assertThat(result?.updatedAt)
            .isNotNull
        Assertions.assertThat(result?.title)
            .isEqualTo("테스트 일정")
        Assertions.assertThat(result?.description)
            .isEqualTo("테스트")
    }

    @Test
    fun saveAllTest() {
        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            }
        )

        val result = todoRepositoryImpl.saveAll(todoList)

        Assertions.assertThat(result)
            .isEqualTo(true)
    }

    @Test
    fun findOneTest() {

        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정1"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정2"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정3"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            }
        )

        todoRepositoryImpl.saveAll(todoList)

        var result = todoRepositoryImpl.findOne(2)

        println(result)

        Assertions.assertThat(result).isNotNull
        Assertions.assertThat(result?.title).isEqualTo("테스트 일정2")
    }

    @Test
    fun updateTest() {

        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        val insertTodo = todoRepositoryImpl.save(todo)

        val todo2 = Todo().apply {
            this.index = insertTodo.index
            this.title = "테스트 일정2"
            this.description = "테스트2"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo2)

        Assertions.assertThat(result).isNotNull
        Assertions.assertThat(result?.index).isEqualTo(insertTodo?.index)
        Assertions.assertThat(result?.title).isEqualTo("테스트 일정2")
        Assertions.assertThat(result?.description).isEqualTo("테스트2")

    }

}