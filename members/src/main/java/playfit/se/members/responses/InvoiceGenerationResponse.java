package playfit.se.members.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceGenerationResponse {
    private boolean success;
    private String message;
    private String invoiceFilePath;
}
