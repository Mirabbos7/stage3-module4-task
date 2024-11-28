package com.mjc.school.repository.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news")
@EntityListeners(AuditingEntityListener.class)
public class News implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Content")
    private String content;

    @Column(name = "Created_Date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "Last_Updated_Date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Author_Id")
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "newstags",
            joinColumns = {@JoinColumn(name = "News_Id")},
            inverseJoinColumns = {@JoinColumn(name = "Tag_Id")}
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public News(
            Long id,
            String title,
            String content,
            LocalDateTime createdDate,
            LocalDateTime lastUpdatedDate,
            Author author,
            List<Tag> tags,
            List<Comment> comments
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.author = author;
        this.tags = tags;
        this.comments = comments;
    }
    public News() {

    }
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News newsModel)) return false;
        return Objects.equals(id, newsModel.id) && Objects.equals(title, newsModel.title) && Objects.equals(content, newsModel.content) && Objects.equals(createdDate, newsModel.createdDate) && Objects.equals(lastUpdatedDate, newsModel.lastUpdatedDate) && Objects.equals(author, newsModel.author)
                && Objects.equals(tags, newsModel.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdDate, lastUpdatedDate, author, tags);
    }
}
