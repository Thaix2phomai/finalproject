package vn.com.techmaster.wineshopping_project.service;

import vn.com.techmaster.wineshopping_project.model.OrderDetail;
import vn.com.techmaster.wineshopping_project.request.CardPaymentRequest;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;

import javax.servlet.http.HttpSession;

public interface PaymentService {
    public void payByMasterCard (HttpSession session, CardPaymentRequest cardPaymentRequest);
}
