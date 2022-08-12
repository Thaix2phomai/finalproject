package vn.com.techmaster.wineshopping_project.service;

import groovy.transform.AutoImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.exception.CardException;
import vn.com.techmaster.wineshopping_project.model.*;
import vn.com.techmaster.wineshopping_project.repository.MasterCardRepo;
import vn.com.techmaster.wineshopping_project.repository.OrderLineRepo;
import vn.com.techmaster.wineshopping_project.repository.OrderRepo;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.CardPaymentRequest;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImple implements PaymentService {

    @Autowired
    private MasterCardRepo masterCardRepo;

    @Autowired
    private CartService cartService;

    @Override
    public void payByMasterCard(HttpSession session, CardPaymentRequest cardPaymentRequest) {
        Optional<MasterCard> masterCard = masterCardRepo.findMasterCardByCardNumber(cardPaymentRequest.getCardNumber());
        if (masterCard.isPresent()) {
            MasterCard currentMasterCard = masterCard.get();
            if (currentMasterCard.getExpiryDate().equals(cardPaymentRequest.getExpiryDate()) && currentMasterCard.getCvv().equals(cardPaymentRequest.getCvv()) && currentMasterCard.getName().equalsIgnoreCase(cardPaymentRequest.getName())) {
                OrderRequest orderRequest = (OrderRequest) session.getAttribute("order");
                cartService.completeOrder(orderRequest, session);
            }
            if (!currentMasterCard.getExpiryDate().equals(cardPaymentRequest.getExpiryDate())) {
                throw new CardException("wrongexpirydate");
            }
            if (!currentMasterCard.getCvv().equals(cardPaymentRequest.getCvv())) {
                throw new CardException("wrongcvv");
            }
            if (!currentMasterCard.getName().equals(cardPaymentRequest.getName())) {
                throw new CardException("wrongname");
            }
        } else {
            throw new CardException("Your card not exist");
        }

    }
}