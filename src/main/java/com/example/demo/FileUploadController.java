package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.storage.StorageFileNotFoundException;
import com.example.demo.storage.StorageService;

@Controller
public class FileUploadController implements CommandLineRunner {

    @Autowired
    ConversionJdbcRepository repository;

    private final StorageService storageService;
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        LOGGER.info("-----------------------------------------------");

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));



        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);

        TesseractExample OCR = new TesseractExample();
        String converted = OCR.converter("./upload-dir/" + file.getOriginalFilename());

        repository.insert(new Conversion(
                2,
                new Timestamp(System.currentTimeMillis()),
                file.getOriginalFilename(),
                (int) file.getSize()
        ));

        redirectAttributes.addFlashAttribute("message",
                "You successfully converted " + file.getOriginalFilename() + "!");

        addToFile(converted, file.getOriginalFilename());

        redirectAttributes.addFlashAttribute("outputFile", FilenameUtils.removeExtension(file.getOriginalFilename()) + ".txt");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    public void addToFile(String convertedText, String fileName) {

        fileName = FilenameUtils.removeExtension(fileName);

        File file = new File("./converted-dir/" + fileName + ".txt");

        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(convertedText);
            writer.close();
        } catch (IOException ex) {
            LOGGER.error("File" + fileName + "not created");
        }
    }

    public void run(String...args) throws Exception {
        LOGGER.info("Conversion id 1 -> {}", repository.findById(1));
    }

}
