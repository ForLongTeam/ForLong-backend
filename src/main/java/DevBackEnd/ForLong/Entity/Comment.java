package DevBackEnd.ForLong.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column
    private LocalDateTime createAt;

    @Column
    private String createBy;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }

}
