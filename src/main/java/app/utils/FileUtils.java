package app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

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