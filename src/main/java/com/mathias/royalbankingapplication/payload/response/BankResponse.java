package com.mathias.royalbankingapplication.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BankResponse<S> {
    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;

    public BankResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public BankResponse(String responseCode, String responseMessage, AccountInfo accountInfo) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.accountInfo = accountInfo;
    }

    public BankResponse(String message, String fileurl){

        this.responseMessage = message;
    }
}
