package com.medibooking.bookingserviceserver.controllers;

import com.medibooking.bookingserviceserver.dtos.appointment.AppointmentGetDto;
import com.medibooking.bookingserviceserver.dtos.appointment.AppointmentPostDto;
import com.medibooking.bookingserviceserver.dtos.appointment.AppointmentPutDto;
import com.medibooking.bookingserviceserver.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/management/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentGetDto> add(@RequestBody AppointmentPostDto appointmentPostDto) {
        AppointmentGetDto appointmentGetDto = appointmentService.createAppointment(appointmentPostDto);
        return ResponseEntity.ok(appointmentGetDto);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentGetDto> modify(@PathVariable Long appointmentId, @RequestBody AppointmentPutDto appointmentPutDto) {
        AppointmentGetDto appointmentGetDto = appointmentService.modifyAppointment(appointmentId, appointmentPutDto);
        return ResponseEntity.ok(appointmentGetDto);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity delete(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR','ROLE_PATIENT')")
    public ResponseEntity<List<AppointmentGetDto>> getAll() {
        List<AppointmentGetDto> appointmentList = appointmentService.getAll();
        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentGetDto> findById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.findAppointmentById(appointmentId));
    }

    @GetMapping(value = "/search", params = "patientId")
    public ResponseEntity<List<AppointmentGetDto>> findByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.findAppointmentsByPatient(patientId));
    }

    @GetMapping(value = "/search", params = "doctorId")
    public ResponseEntity<List<AppointmentGetDto>> findByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.findAppointmentsByDoctor(doctorId));
    }

    @GetMapping(value = "/search", params = {"patientId", "date"})
    public ResponseEntity<List<AppointmentGetDto>> findByPatientAndDate(@PathVariable Long patientId, LocalDate date) {
        return ResponseEntity.ok(appointmentService.findAppointmentsOfAPatientByDate(patientId, date));
    }

    @GetMapping(value = "/search", params = {"doctorId", "date"})
    public ResponseEntity<List<AppointmentGetDto>> findByDoctorAndDate(@PathVariable Long doctorId, LocalDate date) {
        return ResponseEntity.ok(appointmentService.findAppointmentsOfADoctorByDate(doctorId, date));
    }
}
