package com.cryptopayments.payment_core.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeService {

    public byte[] generateQrCode(String content, int size) {

        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    size,
                    size);

            BufferedImage image =
                    MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();

            ImageIO.write(image, "PNG", output);

            return output.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }
    }
}