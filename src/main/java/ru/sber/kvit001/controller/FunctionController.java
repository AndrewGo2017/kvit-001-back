package ru.sber.kvit001.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvit001.handler.MainHandler;
import ru.sber.kvit001.model.misc.CommonReqs;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/func")
public class FunctionController {
    private static MediaType STREAM_MEDIA_TYPE = MediaType.parseMediaType("application/octet-stream");
    private static MediaType TEXT_MEDIA_TYPE = MediaType.parseMediaType("text/plain");

    @Autowired
    private MainHandler mainHandler;

    @PostMapping
    public ResponseEntity upload(@RequestParam(value = "file")MultipartFile file, @RequestParam(value = "func") int func) throws Exception {
        try {
            if (file == null) {
                return createResponseError("Не выбран файл!", 400, TEXT_MEDIA_TYPE);
            }
            if (func == 1){
                ByteArrayOutputStream baos = mainHandler.getPdf(file);
                return createResponseOk(baos, STREAM_MEDIA_TYPE, "filename", "kvit-000.pdf");
            } else {
                List<CommonReqs> table = mainHandler.getTable(file);
                return ResponseEntity.ok()
                        .body(table);
            }
        } catch (Exception e) {
            return createResponseError(e.toString(), 500, TEXT_MEDIA_TYPE);
        }
    }


    private ResponseEntity createResponseOk(ByteArrayOutputStream baos, MediaType contentType, String headerName, String headerValue ){
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(headerName, headerValue)
                .body(baos.toByteArray());
    }

    private ResponseEntity createResponseError(String message, int status, MediaType contentType ){
        return ResponseEntity
                .status(status)
                .contentType(contentType)
                .body(message);
    }
}
