import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Profile } from '../model/profile';
import { AlertsService } from './alerts.service';
import { Alert } from '../model/alert';
import { WebSocketService } from "../websocket/websocket.service";
import { TaskChannelService } from "../websocket/task-channel.service";
import { AlertChannelService } from "../websocket/alert-channel.service";


@Component({
  selector: 'alerts',
  templateUrl: 'alerts.html',
  styleUrls: ['../../assets/css/sb-admin-2.css'],
  providers: [AlertsService, WebSocketService, TaskChannelService, AlertChannelService]
})
export class AlertsComponent { 

    alertsFullList : [Alert];
    alertsList : [Alert];
    bellColor = '#337ab7';

  	constructor(private router: Router, 
                private alertsService: AlertsService,
                private taskChannelService:TaskChannelService,
                private webSocketService:WebSocketService) { 

      this.webSocketService.start( 'ws://tvss.me:7000/recognition-service/ws-alerts' );
        this.taskChannelService.observableData.subscribe( ( data:Object ) => {
            this.addAlert(data);
        });
    }

    setDefaultBellColor() {
      this.bellColor = '#337ab7';
    }


    private addAlert(data: any) {
      let alert = <Alert>({
        id: data.id,
        type: data.type,
        message: data.message,
        time: data.date,
        refs: data.refs
      
      });

      this.alertsFullList.push(alert);
      this.bellColor = '#e81f1f';
      this.alertsList.unshift(alert);
      if(this.alertsList.length > 3) {
        this.alertsList.pop();
      }

    }

  	ngOnInit() { 

        console.log('Fetching user alerts');
        this.alertsService.getAlerts().subscribe(
                  response => {
                    console.log("User alerts:");
                    console.log(response);
                    this.alertsFullList = response;
                  },
                  error => {
                      if(error.status === 401) {
                          localStorage.removeItem('token');
                          this.router.navigateByUrl('/login');
                      } else {
                          alert(error.json());
                      }
                  }
                );

        this.alertsService.getRecentAlerts().subscribe(
                  response => {
                    this.alertsList = response;
                  },
                  error => {
                      if(error.status === 401) {
                          localStorage.removeItem('token');
                          this.router.navigateByUrl('/login');
                      } else {
                          alert(error.json());
                      }
                  }
                );

  	}

  	ngOnDestroy() {
  	}

}
