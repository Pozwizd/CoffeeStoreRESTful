package spaceLab.service.Imp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class QRCodeGeneratorService {

    private final QRCodeWriter qrCodeWriter;

    public QRCodeGeneratorService(QRCodeWriter qrCodeWriter) {
        this.qrCodeWriter = qrCodeWriter;
    }

    public byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] qrCodeBytes = pngOutputStream.toByteArray();

            log.info("QR-код успешно создан для текста: {}", text);

            return qrCodeBytes;
        }
    }
}
