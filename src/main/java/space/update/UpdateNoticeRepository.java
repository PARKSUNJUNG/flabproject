package space.update;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateNoticeRepository extends JpaRepository<UpdateNotice, Long> {

    @Query("""
            select n
            from UpdateNotice n
            order by n.createdAt desc
            """)
    List<UpdateNotice> findAdminList(Pageable pageable);

    @Query("""
            select count(n)
            from UpdateNotice n
            """)
    long countAdminList();
}
