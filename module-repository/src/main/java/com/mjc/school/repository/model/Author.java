package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
@EntityListeners(AuditingEntityListener.class)
public class Author implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Created_Date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "Last_Updated_Date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<News> news;



    public Author(Long id, String name, LocalDateTime createdDate, LocalDateTime lastUpdatedDate,
                  List<News> news) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.news = news;
    }
    public Author() {}
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(createdDate, that.createdDate) && Objects.equals(lastUpdatedDate, that.lastUpdatedDate)
                && Objects.equals(news, that.news);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdDate, lastUpdatedDate, news);
    }
}
