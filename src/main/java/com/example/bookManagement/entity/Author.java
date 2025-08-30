package com.example.bookManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;

    private String name;
    private String gender;
    private String email;
    private String phone;
    private String dateOfBirth;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "author")
    private List<AuthorSocial> authorSocial; // list

//    @OneToMany(mappedBy = "authors",
//               cascade = CascadeType.ALL)
//    private List<AuthorPublishBookRelation> publishBook;

    @ManyToMany(mappedBy = "authors")
//    @JsonIgnore
    private List<PublishBook> publishBooks;
}
