package com.mjc.school.repository.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tags")
public class Tag implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<News> news;

    public Tag(
            Long id, String name
    ) {
        this.id = id;
        this.name = name;
    }

    public Tag() {

    }

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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag tagModel)) return false;
        return Objects.equals(id, tagModel.id) && Objects.equals(name, tagModel.name) && Objects.equals(news, tagModel.news);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, news);
    }
}
