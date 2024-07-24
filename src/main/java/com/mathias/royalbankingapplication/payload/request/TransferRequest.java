package com.mathias.royalbankingapplication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    private String destinationAccountNo;

    private String sourceAccountNo;

    private BigDecimal amount;
}
