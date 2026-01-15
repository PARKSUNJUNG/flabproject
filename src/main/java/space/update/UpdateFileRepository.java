package space.update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateFileRepository extends JpaRepository<UpdateFile, Long> {
}
