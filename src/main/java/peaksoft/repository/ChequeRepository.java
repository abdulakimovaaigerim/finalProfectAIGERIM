package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entities.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select avg(c.grandTotal) from Cheque c where c.user.restaurant.id=:restId")
    Double getAverageSum(Long restId);

    @Override
    Page<Cheque> findAll(Pageable pageable);

}
