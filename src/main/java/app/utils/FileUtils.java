package app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import app.model.exception.InvalidFileType;
import app.utils.ImageFileVerificator.ImageType;

public class FileUtils {
    private static final String UPLOAD_DIR = "uploads/";

    public static String handleUploads(MultipartFile file, ImageType type) throws IllegalStateException, IOException {
        Path uploadDir = getAbsolutePathFromRelative(UPLOAD_DIR);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String randomNewFileName = UUID.randomUUID().toString();
        String fileExt = "." + type.name().toLowerCase();

        Path newFileSave = Path.of(randomNewFileName + fileExt);

        file.transferTo(uploadDir.resolve(newFileSave));

        return randomNewFileName + fileExt;
    }

    public static List<String> handleBatchUploadsImage(List<MultipartFile> files) throws IOException {

        List<String> imgUrl = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            ImageType type = ImageFileVerificator.verifyImageType(multipartFile.getInputStream());

            if (type == ImageType.UNKNOWN) {
                throw new InvalidFileType("File gambar tidak valid! harap upload png, webp, jpg dan gif",
                        "user/my-profile", "profilePicture");
            }

            imgUrl.add(handleUploads(multipartFile, type));
        }

        return imgUrl;
    }

    public static Path getAbsolutePathFromRelative(String relativePath) {
        return Paths.get(relativePath).toAbsolutePath();
    }

    public static boolean deleteImage(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Nama file tidak boleh kosong");
            return false;
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR, fileName).normalize();

            // Cek apakah file ada
            if (!Files.exists(filePath)) {
                System.out.println("File tidak ditemukan: " + filePath.toAbsolutePath());
                return false;
            }

            Files.delete(filePath); // Hapus file
            System.out.println("File berhasil dihapus: " + filePath.toAbsolutePath());
            return true;

        } catch (IOException e) {
            System.out.println("Gagal menghapus file: " + e.getMessage());
            return false;
        }
    }
}