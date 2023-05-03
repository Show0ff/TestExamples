package org.tasks.Third;

import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.GET})
public class ResumeUploadController {

    private static final String UPLOAD_DIRECTORY = "src/main/resources/CV";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @SneakyThrows
    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            File uploadDir = new File(UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            File uploadedFile = new File(uploadDir.getAbsolutePath() + "/" + file.getOriginalFilename());

            file.transferTo(uploadedFile);

            redirectAttributes.addFlashAttribute("message", "Фото успешно загружено: " + file.getOriginalFilename());
        }
        return "redirect:/";
    }
}