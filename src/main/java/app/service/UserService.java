package app.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.model.dto.UpdateUserProfileRequest;
import app.model.entity.UserProfileData;
import app.model.exception.InvalidFileType;
import app.repository.UserRepository;
import app.utils.FileUtils;
import app.utils.ImageFileVerificator;
import app.utils.ImageFileVerificator.ImageType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public UserProfileData getCurrentUserData(String username) {
        return repo.getUserProfile(username);
    }

    public UserProfileData updateUserData(UpdateUserProfileRequest updateReq, HttpServletRequest request)
            throws IOException {
        MultipartFile img = updateReq.getProfilePicture();
        Claims claims = (Claims) request.getSession(false).getAttribute("claims");

        System.out.println(claims);

        int id = claims.get("id", Integer.class);

        String imgFileName = null;

        if (!img.isEmpty()) {
            InputStream stream = img.getInputStream();
            ImageType mediaType = ImageFileVerificator.verifyImageType(stream);

            if (mediaType == ImageType.UNKNOWN) {
                throw new InvalidFileType("File gambar tidak valid! harap upload png, webp, jpg dan gif");
            }

            imgFileName = FileUtils.handleUploads(img, mediaType);
        }

        // dikasih null supaya gak diupdate. Gua bingung mau diapain lagi mennn
        UserProfileData data = new UserProfileData(null, updateReq.getFullName(), updateReq.getEmail(), imgFileName,
                updateReq.getGender(), null);

        UserProfileData result = repo.updateUser(data, id);

        return result;
    }
}
