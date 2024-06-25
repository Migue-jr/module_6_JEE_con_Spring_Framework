package cl.alkewallet.alkewalletm6v2.service;



import cl.alkewallet.alkewalletm6v2.model.Transaction;
import cl.alkewallet.alkewalletm6v2.model.User;
import cl.alkewallet.alkewalletm6v2.repository.TransactionRepository;
import cl.alkewallet.alkewalletm6v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction saveTransaction(Transaction transaction, User user) {
        transaction.setDate(LocalDateTime.now());
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}