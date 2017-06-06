package com.maxclay.controller;

import com.google.common.collect.Lists;
import com.maxclay.model.Alert;
import com.maxclay.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@RestController
public class AlertsController {

    public static final int RECENT_ALERTS_NUM = 3;

    private final AlertService alertService;

    @Autowired
    public AlertsController(AlertService alertService) {
        this.alertService = alertService;
    }

    @RequestMapping(value = "/alerts", method = RequestMethod.GET)
    public ResponseEntity getUserAlerts(Principal principal) {
        List<Alert> alertList = alertService.getAlertsByOwner(principal.getName());
        return ResponseEntity.ok(Lists.reverse(alertList));
    }

    @RequestMapping(value = "/recent-alerts", method = RequestMethod.GET)
    public ResponseEntity getRecentAlerts(Principal principal) {

        // todo limit should be specified at querying level
        List<Alert> alertList = alertService.getAlertsByOwner(principal.getName());
        int lastIndex = (RECENT_ALERTS_NUM <= alertList.size()) ? RECENT_ALERTS_NUM : alertList.size();
        return ResponseEntity.ok(Lists.reverse(alertList).subList(0, lastIndex));
    }

}
