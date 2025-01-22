package DevBackEnd.ForLong.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime ctDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    @PrePersist
    protected void onCreate(){
        this.ctDate = LocalDateTime.now();
    }
}
