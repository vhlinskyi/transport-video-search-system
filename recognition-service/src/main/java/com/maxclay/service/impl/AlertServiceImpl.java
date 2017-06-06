package com.maxclay.service.impl;

import com.maxclay.controller.AlertsWebSocketHandler;
import com.maxclay.model.Alert;
import com.maxclay.model.AlertType;
import com.maxclay.model.Task;
import com.maxclay.model.WantedTransport;
import com.maxclay.repository.AlertRepository;
import com.maxclay.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@Service
public class AlertServiceImpl implements AlertService {

    private final AlertsWebSocketHandler alertsWebSocketHandler;
    private final AlertRepository alertRepository;

    @Autowired
    public AlertServiceImpl(AlertsWebSocketHandler alertsWebSocketHandler, AlertRepository alertRepository) {
        this.alertsWebSocketHandler = alertsWebSocketHandler;
        this.alertRepository = alertRepository;
    }

    @Override
    public void sendAlert(Task task, WantedTransport wantedTransport) {

        if (task == null) {
            throw new IllegalArgumentException("Task can not be null");
        }

        if (wantedTransport == null) {
            throw new IllegalArgumentException("Wanted transport can not be null");
        }

        Alert alert = new Alert();
        alert.setType(AlertType.SUSPICIOUS_FOUND);
        alert.setOwner(task.getOwner());
        alert.setMessage(String.format("Suspicious transport found with number plate '%s'", wantedTransport.getNumberPlate()));
        alert.addRef("task_id", task.getId());
        alert.addRef("wanted_transport", wantedTransport);
        alertRepository.save(alert);
        alertsWebSocketHandler.sendAlert(alert);
    }

    @Override
    public List<Alert> getAlertsByOwner(String owner) {
        return alertRepository.getAllByOwner(owner);
    }
}
