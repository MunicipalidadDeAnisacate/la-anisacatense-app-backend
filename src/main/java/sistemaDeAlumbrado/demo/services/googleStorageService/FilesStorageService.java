package sistemaDeAlumbrado.demo.services.googleStorageService;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FilesStorageService {
    // Inyectado automáticamente por Spring Cloud GCP
    @Autowired
    private Storage storage;

    // nombre de Bucket
    @Value("${gcs.bucket.files.name}")
    private String bucketName;


    public String uploadFile(MultipartFile file) throws IOException {
        String safeName = SafeNameGetter.getSafeName(file.getOriginalFilename());

        String objectName = UUID.randomUUID() + "_" + safeName;

        BlobId blobId = BlobId.of(bucketName, objectName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        return String.format(objectName);
    }


    public String generateSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();
        URL signedUrl = storage.signUrl(blobInfo, 20, TimeUnit.MINUTES);
        return signedUrl.toString();
    }


    public boolean deleteFileByUrl(String objectName) {
        return storage.delete(bucketName, objectName);
    }
}

