package io._29cu.usmserver.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io._29cu.usmserver.core.service.StorageService;

@Controller
@RequestMapping("/api/0/developer")
public class FileController {

    @Autowired
    private StorageService storageService;
//
//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService
//                .loadAll()
//                .map(path ->
//                        MvcUriComponentsBuilder
//                                .fromMethodName(FileController.class, "serveFile", path.getFileName().toString())
//                                .build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }

//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
//                .body(file);
//    }
//
//    @RequestMapping(path = "/upload", method = RequestMethod.POST)
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//        storageService.store(file);
//        String response = "{'originalName': '" + file.getOriginalFilename() + "', 'generatedName': '" + file.getOriginalFilename() + "'}";
//        response = response.replace("'", "\"");
//        ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }

}
