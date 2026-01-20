package space.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByActiveTrue();

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
