package com.example.pi_project.Services;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    public Agenda createAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public List<Agenda> getAgendasByMeetingTitle(String meetingTitle) {
        return agendaRepository.findByMeetingTitle(meetingTitle);
    }
}
