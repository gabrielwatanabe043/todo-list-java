package com.gabriel.todolist.repositories;

import com.gabriel.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskReposiroty extends JpaRepository<Task, UUID> {
    List<Task> findAllByIdUser(UUID idUser);
}
