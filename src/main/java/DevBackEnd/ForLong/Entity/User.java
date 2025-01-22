package DevBackEnd.ForLong.Entity;

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
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String join_id;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String provider;

    @Column
    private String provider_id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

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

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setUser(this);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setUser(this);
    }

}
