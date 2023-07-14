package kz.alibek.googleauthservice.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CodeController {

    private final GoogleAuthenticator gAuth;

    @SneakyThrows
    @GetMapping("/generate/{userName}")
    public void generate(@PathVariable String userName, HttpServletResponse response) {
        GoogleAuthenticatorKey key = gAuth.createCredentials(userName);

        //I've decided to generate QRCode on backend site
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("alibek", userName, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        //Simple writing to outputstream
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @PostMapping("/validate/key")
    public Validation validateKey(@RequestBody ValidateCodeDto body) {
        return new Validation(gAuth.authorizeUser(body.getUsername(), body.getVerificationCode()));
    }

    @Data
    public static class ValidateCodeDto {
        private String username;
        private Integer verificationCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Validation {
        private Boolean validated;
    }

}
