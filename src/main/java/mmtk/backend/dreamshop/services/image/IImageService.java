package mmtk.backend.dreamshop.services.image;

import mmtk.backend.dreamshop.dtos.ImageDto;
import mmtk.backend.dreamshop.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file, Long imageId);
}
