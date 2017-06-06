package com.maxclay.service;

import com.maxclay.model.Alert;
import com.maxclay.model.Task;
import com.maxclay.model.WantedTransport;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface AlertService {

    void sendAlert(Task task, WantedTransport wantedTransport);

    List<Alert> getAlertsByOwner(String owner);
}
