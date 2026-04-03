package project.projectmanagernovel.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfig {
    public static Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "do02hfkjk",
                "api_key", "788334457271712",
                "api_secret", "N7sjVGVTwqhaAaUzDMwLd4Ynn74",
                "secure", true
        ));
    }
}