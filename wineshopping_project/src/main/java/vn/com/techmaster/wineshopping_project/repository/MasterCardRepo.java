package vn.com.techmaster.wineshopping_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.techmaster.wineshopping_project.model.MasterCard;

import java.util.Optional;

public interface MasterCardRepo extends JpaRepository<MasterCard, String> {
    Optional<MasterCard> findMasterCardByCardNumber (String cardNumber);

    Optional<MasterCard> findMasterCardByCvv (String cvv);

}
