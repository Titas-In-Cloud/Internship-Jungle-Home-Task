package com.task.luminorjungletask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @RequestMapping(method= RequestMethod.POST, value="/payments")
    public void addPayment(@RequestParam Double amount, @RequestParam String iban) {
        paymentService.addPayment(amount, iban);
    }

    @RequestMapping(method= RequestMethod.POST, value="/payment-files")
    public void readCSV(@RequestParam("file") MultipartFile fileToRead) throws IOException {
        paymentService.readCSV(fileToRead);
    }

    @RequestMapping("/payments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
