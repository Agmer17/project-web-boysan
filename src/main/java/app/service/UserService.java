package app.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.model.dto.UpdateUserProfileRequest;
import app.model.exception.AuthCredentialsException;
import app.model.exception.InvalidFileType;
import app.model.exception.PostsDataNotValidException;
import app.model.pojo.UserProfileData;
import app.repository.UserRepository;
import app.utils.FileUtils;
import app.utils.ImageFileVerificator;
import app.utils.ImageFileVerificator.ImageType;
import io.jsonwebtoken.Claims;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public UserProfileData getCurrentUserData(String username) {
        return repo.getUserProfile(username);
    }

    public void updateUserData(UpdateUserProfileRequest updateReq, Claims claims) {
        int id = claims.get("id", Integer.class);
        String currentUsername = claims.get("username", String.class);

        UserProfileData oldData = repo.getUserProfile(currentUsername);
        MultipartFile img = updateReq.getProfilePicture();

        if (!oldData.getEmail().equals(updateReq.getEmail())) {
            System.out.println(oldData.getEmail());
            System.out.println(updateReq.getEmail());

            if (repo.existByEmail(updateReq.getEmail())) {
                // todo bikin exception sendiri buat pas update data
                throw new AuthCredentialsException("Data email sudah ada!",
                        "user/my-profile", "email");
            }
        }

        String imgFileName = null;
        try {
            if (!img.isEmpty()) {
                String oldProfilePic = repo.getUserProfile(id).getProfilePicture();
                InputStream stream = img.getInputStream();
                ImageType mediaType = ImageFileVerificator.verifyImageType(stream);

                if (mediaType == ImageType.UNKNOWN) {
                    throw new InvalidFileType("File gambar tidak valid! harap upload png, webp, jpg dan gif",
                            "user/my-profile", "profilePicture");
                }

                imgFileName = FileUtils.handleUploads(img, mediaType);

                if (oldProfilePic != null) {
                    FileUtils.deleteImage(oldProfilePic);
                }
            }
        } catch (IOException e) {
            throw new PostsDataNotValidException("data yang kamu kirimkan tidak valid", "user/my-profile");
        }

        UserProfileData data = new UserProfileData(null, updateReq.getFullName(), updateReq.getEmail(),
                updateReq.getPhoneNumber(), imgFileName, updateReq.getGender(), null);

        repo.updateUser(data, id);
    }
}
