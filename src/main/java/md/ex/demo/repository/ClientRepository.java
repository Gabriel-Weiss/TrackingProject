package md.ex.demo.repository;

import md.ex.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByClientId(String clientId);

    Optional<Client> findByClientId(String clientId);
}