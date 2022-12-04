package com.example.todo.controller.api.todo

import com.example.todo.model.http.TodoDto
import com.example.todo.service.TodoService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/todo")
class TodoApiController(
    val todoService: TodoService
) {
    // R
    @GetMapping(path = [""])
    fun read(@RequestParam(required = false) index: Int?): ResponseEntity<Any?> {

        return index?.let {
            todoService.read(it)
        }?.let {    // Exist index
            return ResponseEntity.ok(it)
        }?: kotlin.run {      // no exist index
            return ResponseEntity
                    .status(HttpStatus.MOVED_PERMANENTLY)
                    .header(HttpHeaders.LOCATION, "/api/todo/all")
                    .build()
        }

    }
    @GetMapping(path = ["/all"])
    fun readAll(): MutableList<TodoDto> {
        return todoService.readAll()

    }

    // C
    @PostMapping("")
    fun create(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoService.create(todoDto)
    }

    // U
    @PutMapping("")
    fun update(@Valid @RequestBody todoDto: TodoDto): ResponseEntity<TodoDto> {
        todoDto.let {
            todoService.update(todoDto)
        }?.let {
            return ResponseEntity.ok(it)
        }?: kotlin.run {
            return ResponseEntity.status(100).build()
        }

    }

    // D
    @DeleteMapping("/{index}")
    fun delete(@PathVariable(name = "index") _index: Int): ResponseEntity<Any> {

        if(!todoService.delete(_index)) {
            return ResponseEntity.status(500).build()
        }
        return ResponseEntity.ok().build()
    }
}