package spaceLab.service.Imp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class QRCodeGeneratorServiceTest {

    @InjectMocks
    private QRCodeGeneratorService qrCodeGeneratorService;

    @Mock
    private QRCodeWriter qrCodeWriter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        qrCodeGeneratorService = new QRCodeGeneratorService(qrCodeWriter);
    }

    @Test
    public void testGenerateQRCodeImage() throws WriterException, IOException {
        String text = "Test QR Code";
        int width = 250;
        int height = 250;

        // Создаем mock для BitMatrix с нужными размерами
        BitMatrix bitMatrixMock = new BitMatrix(width, height);

        // Мокаем поведение QRCodeWriter
        when(qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)).thenReturn(bitMatrixMock);

        // Мокаем ByteArrayOutputStream
        ByteArrayOutputStream outputStreamMock = mock(ByteArrayOutputStream.class);

        // Генерация QR кода
        byte[] result = qrCodeGeneratorService.generateQRCodeImage(text, width, height);

        // Проверка, что QRCodeWriter был вызван
        verify(qrCodeWriter, times(1)).encode(text, BarcodeFormat.QR_CODE, width, height);

        // Проверка, что результат не пустой
        assertNotNull(result);
        assertTrue(result.length > 0);
    }
}
