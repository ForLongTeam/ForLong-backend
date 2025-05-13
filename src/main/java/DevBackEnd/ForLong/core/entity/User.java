package DevBackEnd.ForLong.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    private String password;

    @Column(nullable = false)
    private String nickname;

    // 이메일은 선택입력
    private String email;

    private String phone;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Column
    private String role;

    @Column
    private String profileImage;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    /**
     * 중복 예약이 되면 안됨.
     * */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private UserLocation userLocation;

//    public void addPost(Post post) {
//        posts.add(post);
//        post.setUser(this);
//    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setUser(this);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setUser(this);
    }

}
