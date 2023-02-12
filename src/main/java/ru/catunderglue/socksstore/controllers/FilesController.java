package ru.catunderglue.socksstore.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.catunderglue.socksstore.services.FilesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("api/files")
@Tag(name = "Файлы", description = "Методы для работы с файлами")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "export/socks")
    @Operation(
            summary = "Экспорт носков в файл"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Носки успешно выгружены",
            content = @Content(
                    mediaType = "multipart/form-data"
            )
    )
    public ResponseEntity<InputStreamResource> exportRecipesDataFile() throws FileNotFoundException {
        File file = filesService.getSocksDataFileInfo();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Импорт носков файлом"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Носки успешно загружены"
    )
    @PostMapping(value = "import/socks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importSocksDataFile(@RequestParam MultipartFile file) {
        try {
            filesService.importSocksDataFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping(value = "export/operations")
    @Operation(
            summary = "Экспорт операций в файл"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Операции успешно выгружены",
            content = @Content(
                    mediaType = "multipart/form-data"
            )
    )
    public ResponseEntity<InputStreamResource> exportOperationsDataFile() throws FileNotFoundException {
        File file = filesService.getOperationsDataFileInfo();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Operations.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Импорт операций файлом"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Операции успешно загружены"
    )
    @PostMapping(value = "import/operations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importOperationsDataFile(@RequestParam MultipartFile file) {
        try {
            filesService.importOperationsDataFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
