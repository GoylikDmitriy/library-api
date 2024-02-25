package com.goylik.libraryservice.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "library")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LibraryBook {
    @Id
    @Column(name = "book_id", nullable = false)
    private long bookId;
    @Column(name = "borrowed_dt")
    private LocalDateTime borrowedTime;
    @Column(name = "return_dt")
    private LocalDateTime returnTime;
}