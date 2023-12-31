package com.gabriel.todolist.controller;

import com.gabriel.todolist.model.Task;
import com.gabriel.todolist.model.User;
import com.gabriel.todolist.repositories.TaskReposiroty;
import com.gabriel.todolist.utils.Utils;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskReposiroty taskReposiroty;

    @GetMapping
    public ResponseEntity<List<Task>> getAll(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        List<Task> tasks = taskReposiroty.findAllByIdUser((UUID) idUser);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody Task task, HttpServletRequest request){
        LocalDate dataCurrent = LocalDate.now();
        if(dataCurrent.isAfter(task.getStartAt()) || dataCurrent.isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicio e data de término deve ser maior que data atual");
        }
        if(task.getStartAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicio não pode ser maior que data de término");
        }





        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser );
        var taskCreated = taskReposiroty.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable UUID id, @RequestBody Task taskRequest, HttpServletRequest request){
        var task = taskReposiroty.findById(id).orElse(null);
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não existe");
        }
        var idUser = request.getAttribute("idUser");

        if(!taskRequest.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário sem permissão para alterar essa tarefa");
        }


        Utils.copyNonNullProperties(taskRequest, task);
        var taskUpdated = taskReposiroty.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);

    }
}
