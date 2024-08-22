package com.sherif.student.controllers;

import com.itextpdf.text.DocumentException;
import com.sherif.student.dtos.RegistrationRequest;
import com.sherif.student.entities.Registration;
import com.sherif.student.entities.User;
import com.sherif.student.responses.RegistrationResponse;
import com.sherif.student.services.RegistrationService;
import com.sherif.student.utils.PdfUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    public static final String REGISTRATION_CANCELLED = "Registration cancelled";
    public static final String REGISTERED_SUCCESSFULLY = "Registered successfully";
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerToCourse(@RequestBody RegistrationRequest request, @AuthenticationPrincipal User user) {
        registrationService.registerToCourse(Long.valueOf(user.getId()), request);
        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .status("Created")
                .message(REGISTERED_SUCCESSFULLY)
                .build();
        return ResponseEntity.ok(registrationResponse);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<RegistrationResponse> cancelCourseRegistration(@RequestBody RegistrationRequest request, @AuthenticationPrincipal User user) {
        registrationService.cancelRegistration(Long.valueOf(user.getId()), request);
        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .status("Deleted")
                .message(REGISTRATION_CANCELLED)
                .build();
        return ResponseEntity.ok(registrationResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Registration>> getRegistrationsForUser(@AuthenticationPrincipal User user) {
        List<Registration> registrations = registrationService.getRegistrationsForUser(Long.valueOf(user.getId()));
        return ResponseEntity.ok(registrations);
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf(@AuthenticationPrincipal User user) throws IOException, DocumentException {
        // Get all registrations for the authenticated user
        List<Registration> registrations = registrationService.getRegistrationsForUser(Long.valueOf(user.getId()));

        // Convert registrations to a format suitable for PDF generation
        List<Map<String, String>> queryResults = registrations.stream().map(registration -> Map.of(
                "Course Name", registration.getCourse().getName(),
                "Registration Date", registration.getRegistrationDate().toString()
        )).toList();

        // Generate the PDF stream
        ByteArrayOutputStream pdfStream = PdfUtils.generatePdfStream(queryResults);

        // Set the headers for PDF response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=registrations.pdf");
        headers.setContentLength(pdfStream.size());

        // Return the PDF as a response
        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }

}

