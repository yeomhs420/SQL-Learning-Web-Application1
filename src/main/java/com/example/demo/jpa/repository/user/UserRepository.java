package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
    @Query(value = "SELECT * FROM User WHERE userid = :userid", nativeQuery = true)
    List<User> findByUserId(String userid);

}
=======
    List<User> findByUserID(String userid);
}
>>>>>>> 6618cc232f5b6986d63898274ca4db9867938b39
