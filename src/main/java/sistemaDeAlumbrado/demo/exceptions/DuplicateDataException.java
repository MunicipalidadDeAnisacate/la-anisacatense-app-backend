package sistemaDeAlumbrado.demo.exceptions;

import java.util.List;

public class DuplicateDataException extends RuntimeException {
    private List<String> duplicatedFields;

    public DuplicateDataException(List<String> duplicatedFields) {
        super("Los siguientes campos ya se encuentran registrados:\n- " + String.join("\n- ", duplicatedFields));
        this.duplicatedFields = duplicatedFields;
    }

    public List<String> getDuplicatedFields() {
        return duplicatedFields;
    }
}

