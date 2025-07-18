package sistemaDeAlumbrado.demo.services.googleStorageService;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PhotosStorageService {

    @Autowired
    private Storage gcsStorage;

    @Value("${gcs.bucket.photos.name}")
    private String bucketName;

   // subir la foto/imagen
    public String uploadPhoto(MultipartFile file, String carpeta) throws IOException {
        String safeName = SafeNameGetter.getSafeName(file.getOriginalFilename());

        String objectName = carpeta + "/"
                + UUID.randomUUID() + "-"
                + safeName;

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        gcsStorage.create(blobInfo, file.getBytes());
        return String.format(objectName);
    }

    public String generateSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();
        return gcsStorage.signUrl(
                blobInfo,
                20,
                TimeUnit.MINUTES,
                SignUrlOption.withV4Signature()  // V4 es más seguro
        ).toString();
    }
}

