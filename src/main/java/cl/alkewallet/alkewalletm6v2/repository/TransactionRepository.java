package cl.alkewallet.alkewalletm6v2.repository;



import cl.alkewallet.alkewalletm6v2.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
}