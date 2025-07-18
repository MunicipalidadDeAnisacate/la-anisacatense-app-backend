package sistemaDeAlumbrado.demo.services.googleStorageService;

import org.springframework.stereotype.Service;

@Service
public class SafeNameGetter {

    public static String getSafeName(String rawName){

        String safeName = rawName
                .trim()
                .replaceAll("\\s+", "_")
                .replaceAll("[^\\w\\-\\.]", "");
        return safeName;
    }
}
