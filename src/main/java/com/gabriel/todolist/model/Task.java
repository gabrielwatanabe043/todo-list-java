package com.gabriel.todolist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_task")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    @Column(length = 50)
    private String title;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Sao_Paulo")
    private LocalDate startAt;
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "America/Sao_Paulo")
    private LocalDate endAt;
    private String priority;
    private UUID idUser;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw  new Exception("Titulo não pode ser maior que 50 caracteres");
        }
    }

}
