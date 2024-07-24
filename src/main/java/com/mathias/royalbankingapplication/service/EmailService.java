package com.mathias.royalbankingapplication.service;

import com.mathias.royalbankingapplication.payload.request.EmailDetails;

public interface EmailService {
    void  sendEmailAlert(EmailDetails emailDetails);
}
