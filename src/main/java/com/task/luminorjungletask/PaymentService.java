package com.task.luminorjungletask;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /***
     * adds a new payment object into a local database.
     *
     * @param amount the amount of money on the payment statement.
     * @param iban International bank account number, used to authenticate the sender.
     */
    public void addPayment(Double amount, String iban) {
        if (validation(amount, iban)) {
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setIban(iban);
            payment.setTimestamp(new Timestamp(System.currentTimeMillis()));
            paymentRepository.save(payment);
        }
    }

    /***
     * Reads CSV file, stores all the information into a  temporary Array List, checks whether the payment
     * information is valid and if it is puts them into a local database.
     *
     * @param fileToRead a CSV file with payment information.
     * @throws IOException
     */
    public void readCSV(MultipartFile fileToRead) throws IOException {

        if (!fileToRead.isEmpty()) {
            try (CSVReader reader = new CSVReader(new FileReader(fileToRead.getName()))) {
                List<String[]> newPayments = reader.readAll();

                reader.close();

                for (int i = 0; i < newPayments.size(); i++) {
                    if (validation(Double.parseDouble(Arrays.toString(newPayments.get(i))), Arrays.toString(newPayments.get(i + 1)))) {
                        Payment payment = new Payment();
                        payment.setAmount(Double.parseDouble(Arrays.toString(newPayments.get(i))));
                        payment.setIban(Arrays.toString(newPayments.get(i + 1)));
                        payment.setTimestamp(new Timestamp(System.currentTimeMillis()));
                        paymentRepository.save(payment);
                    }
                    i++;
                }

            } catch (CsvException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     *
     * @return a list of payments.
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        paymentRepository.findAll().forEach(payments::add);
        return payments;
    }


    /***
     * Checks whether the amount of money on the statement is greater than 0 and IBAN is
     * from a Baltic Country (LT, LV, EE).
     *
     * @param amount the amount of money on the payment statement.
     * @param iban International bank account number, used to authenticate the sender.
     * @return
     */
    public static boolean validation(Double amount, String iban) {
        return amount >= 0 && (iban.startsWith("LT") || iban.startsWith("LV") || iban.startsWith("EE"));
    }
}