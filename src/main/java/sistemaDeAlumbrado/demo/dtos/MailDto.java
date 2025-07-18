package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;

@Data
public class MailDto {
    private String destinity;
    private String subject;
    private String body;

    public MailDto(String destinity, String subject, String body) {
        this.destinity = destinity;
        this.subject = subject;
        this.body = body;
    }
}
