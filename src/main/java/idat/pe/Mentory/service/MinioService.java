package idat.pe.Mentory.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name:mentory-files}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El archivo es obligatorio");
        }
        String objectKey = buildObjectKey(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            ensureBucketExists();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(resolveContentType(file.getContentType()))
                            .build()
            );
            return objectKey;
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo subir el archivo a MinIO");
        }
    }

    public String getPresignedUrl(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El object key es obligatorio");
        }
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectKey)
                            .expiry(60 * 15)
                            .build()
            );
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo generar URL temporal");
        }
    }

    private void ensureBucketExists() throws Exception {
        boolean exists = minioClient.bucketExists(
                io.minio.BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
    }

    private String buildObjectKey(String originalFilename) {
        String safeFilename = originalFilename == null ? "archivo.bin" : originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        LocalDate now = LocalDate.now();
        return "contenidos/" + now.getYear() + "/" + now.getMonthValue() + "/" + UUID.randomUUID() + "-" + safeFilename;
    }

    private String resolveContentType(String contentType) {
        return contentType == null || contentType.isBlank() ? "application/octet-stream" : contentType;
    }
}
