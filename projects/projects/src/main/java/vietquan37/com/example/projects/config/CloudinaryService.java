package vietquan37.com.example.projects.config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import vietquan37.com.example.projects.exception.FileException;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    @Value("${cloudinary.max-file-size}")
    private long maxFileSize; // Maximum allowed file size in bytes

    @Value("${cloudinary.allowed-content-types}")
    private String[] allowedContentTypes; // Allowed content types for upload
@Autowired
    private final Cloudinary cloudinary;


    public String uploadFile(MultipartFile file) throws IOException, FileException {

        if (file.getSize() > maxFileSize) {
            throw new FileException("File size exceeds the maximum allowed size.");
        }


        if (!isContentTypeAllowed(file.getContentType())) {
            throw new FileException("Unsupported file type.");
        }


        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    // Method to check if the content type is allowed
    private boolean isContentTypeAllowed(String contentType) {
        for (String allowedType : allowedContentTypes) {
            if (contentType != null && contentType.startsWith(allowedType)) {
                return true;
            }
        }
        return false;
    }

    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(extractPublicIdFromUrl(publicId), ObjectUtils.emptyMap());
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String filename = parts[parts.length - 1];
        return filename.substring(0, filename.lastIndexOf('.'));
    }
}
