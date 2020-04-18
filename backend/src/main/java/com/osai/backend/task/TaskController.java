package com.osai.backend.task;

// import java.util.List;

// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    private TaskRepository repository;

    public TaskController(TaskRepository repository){
        this.repository = repository;
    }

    //Mappings
    @PostMapping(path="/api/task/createTask")
    public Task createTask(@Valid @RequestBody Task newTask){
        return repository.save(newTask);
    }

    //Get all -- mostly for testing
    @GetMapping(path="/api/task/all")
    public @ResponseBody Iterable<Task> getAllTasks(){
        return repository.findAll();
    }
    
    @GetMapping("/api/task/getTaskById/{id}")
    public Task getTaskById(@PathVariable long id) {
         return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/api/task/deleteTaskById/{id}") 
    public void deleteTaskById(@PathVariable long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    @PutMapping("/api/task/updateTaskById/{id}")
    public Task updateTaskById(@RequestBody Task newTask, @PathVariable long id) {
        return repository.findById(id)
            .map(task -> {
                repository.deleteById(id);
                newTask.setId(id);
                return repository.save(newTask);
            }).orElseGet(() -> {
                newTask.setId(id);
                return repository.save(newTask);
            });
    }
}