package com.mathias.royalbankingapplication.service;

import com.mathias.royalbankingapplication.payload.request.CreditAndDebitRequest;
import com.mathias.royalbankingapplication.payload.request.EnquiryRequest;
import com.mathias.royalbankingapplication.payload.request.TransferRequest;
import com.mathias.royalbankingapplication.payload.response.BankResponse;

public interface UserService {
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditAndDebitRequest creditAndDebitRequest);

    BankResponse debitAccount(CreditAndDebitRequest creditAndDebitRequest);

    BankResponse transfer(TransferRequest request);
}
