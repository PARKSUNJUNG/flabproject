package space.update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateNoticeRepository extends JpaRepository<UpdateNotice, Long> {
}
