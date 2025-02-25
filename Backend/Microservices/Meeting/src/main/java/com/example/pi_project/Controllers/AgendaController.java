package com.example.pi_project.Controllers;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

@RequestMapping("/agendas")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public ResponseEntity<Agenda> createAgenda(@RequestBody Agenda agenda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.createAgenda(agenda));
    }

    @GetMapping("/meeting/{meetingTitle}")
    public ResponseEntity<List<Agenda>> getAgendasByMeetingTitle(@PathVariable String meetingTitle) {
        return ResponseEntity.ok(agendaService.getAgendasByMeetingTitle(meetingTitle));
    }
}

