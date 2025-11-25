package hr.tpopovic.myshowlist.adapter.out;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "show")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract sealed class ShowEntity permits MovieEntity, TvSeriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, length = 65535)
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}