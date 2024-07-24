package com.mathias.royalbankingapplication.service.impl;

import com.mathias.royalbankingapplication.domain.entity.UserEntity;
import com.mathias.royalbankingapplication.payload.request.CreditAndDebitRequest;
import com.mathias.royalbankingapplication.payload.request.EmailDetails;
import com.mathias.royalbankingapplication.payload.request.EnquiryRequest;
import com.mathias.royalbankingapplication.payload.request.TransferRequest;
import com.mathias.royalbankingapplication.payload.response.AccountInfo;
import com.mathias.royalbankingapplication.payload.response.BankResponse;
import com.mathias.royalbankingapplication.repository.UserRepository;
import com.mathias.royalbankingapplication.service.EmailService;
import com.mathias.royalbankingapplication.service.UserService;
import com.mathias.royalbankingapplication.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static com.mathias.royalbankingapplication.utils.AccountUtils.ACCOUNT_NUMBER_FOUND_MESSAGE;
import static com.mathias.royalbankingapplication.utils.AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();
        }

        UserEntity foundUserAccount = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                .responseMessage(ACCOUNT_NUMBER_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUserAccount.getAccountbalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUserAccount.getFirstName() + " " + foundUserAccount.getLastName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExists) {
            return ACCOUNT_NUMBER_NON_EXISTS_MESSAGE;
        }
        UserEntity foundUserAccount = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return foundUserAccount.getFirstName() + " " + foundUserAccount.getOtherName()+ " " + foundUserAccount.getLastName();
    }

    @Override
    public BankResponse creditAccount(CreditAndDebitRequest creditAndDebitRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(creditAndDebitRequest.getAccountNumber());
        if(!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();

        }
        UserEntity userToCredit = userRepository.findByAccountNumber(creditAndDebitRequest.getAccountNumber());

        userToCredit.setAccountbalance(userToCredit.getAccountbalance()
                .add(creditAndDebitRequest.getAmount()));
        userToCredit.setFirstName(creditAndDebitRequest.getFirstName());
     userRepository.save(userToCredit);

     EmailDetails creditAlert = EmailDetails.builder()
             .subject("CREDIT ALERT")
             .recipient(userToCredit.getEmail())
             .messageBody("Your account has been credited with " + creditAndDebitRequest.getAmount() + " \n from " + userToCredit.getFirstName()
             + " " + userToCredit.getLastName() + " \n Your current account balance is " + userToCredit.getAccountbalance())
             .build();
     emailService.sendEmailAlert(creditAlert);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                        .accountBalance(userToCredit.getAccountbalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditAndDebitRequest request) {
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();

        }
        UserEntity userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
          //check for insufficientBalance
        BigInteger availableBalance = userToDebit.getAccountbalance().toBigInteger();

        BigInteger debitAmount = request.getAmount().toBigInteger();

        if(availableBalance.intValue() < debitAmount.intValue()){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_INSUFFICIENT_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_INSUFFICIENT_MESSAGE)
                    .accountInfo(null)
                    .build();
        }else {
            userToDebit.setAccountbalance(userToDebit.getAccountbalance()
                    .subtract(request.getAmount()));
            userRepository.save(userToDebit);

            EmailDetails debitAlert = EmailDetails.builder()
                    .subject("DEBIT ALERT")
                    .recipient(userToDebit.getEmail())
                    .messageBody("The sum of " + request.getAmount() + " has been deducted from your account!" +
                            " Your current account balance is " + userToDebit.getAccountbalance())
                    .build();
            emailService.sendEmailAlert(debitAlert);
        }

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                        .accountBalance(userToDebit.getAccountbalance())
                        .accountNumber(userToDebit.getAccountNumber())
                        .build())

                .build();
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        /**
         * check if the destination account number exists
         * check if amount to send is available
         * deduct the amount to send from sender balance
         * add the send amount to receiver balance
         * then  send alert
         */

        boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNo());
        if(!isDestinationAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_NON_EXISTS_MESSAGE)
                    .build();
        }
        UserEntity sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNo());
        if (request.getAmount().compareTo(sourceAccountUser.getAccountbalance()) > 0){
          return BankResponse.builder()
                  .responseCode(AccountUtils.ACCOUNT_INSUFFICIENT_CODE)
                  .responseMessage(AccountUtils.ACCOUNT_INSUFFICIENT_MESSAGE)
                  .accountInfo(null)
                  .build();
        }

        sourceAccountUser.setAccountbalance(sourceAccountUser.getAccountbalance().subtract(request.getAmount()));
        userRepository.save(sourceAccountUser);

        String sourceUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName();
           EmailDetails debitAlert = EmailDetails.builder()
                   .subject("DEBIT ALERT")
                   .recipient(sourceAccountUser.getEmail())
                   .messageBody("The sum of " + request.getAmount() + " has been deducted from your account. " +
                           "Your current account balance is " + sourceAccountUser.getAccountbalance())
                   .build();
          emailService.sendEmailAlert(debitAlert);

          UserEntity destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNo());
          destinationAccountUser.setAccountbalance(destinationAccountUser.getAccountbalance().add(request.getAmount()));

          userRepository.save(destinationAccountUser);

          EmailDetails creditAlert = EmailDetails.builder()
                  .subject("CREDIT ALERT")
                  .recipient(destinationAccountUser.getEmail())
                  .messageBody("Your Account has been credited with " + request.getAmount() + " from " + sourceUsername +
                          "your current account balance is " + destinationAccountUser.getAccountbalance())
                  .build();
          emailService.sendEmailAlert(creditAlert);


        return BankResponse.builder()
                .responseCode("200")
                .responseMessage("Transfer Successfull")
                .accountInfo(null)
                .build();
    }
}
