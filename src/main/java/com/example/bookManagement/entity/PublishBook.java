package com.example.bookManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublishBook {
    // same as book for learning many-to-many relation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String title;
    private String authorName;
    private Integer price;

    @ManyToMany
    @JoinTable(name = "Author_PublishBook_Relation1",
               joinColumns = @JoinColumn(name = "author_id"),
               inverseJoinColumns = @JoinColumn(name = "publishBook_id"))
    @JsonIgnore /// not showing  ^ create recursive loop
    private List<Author> authors;

}
