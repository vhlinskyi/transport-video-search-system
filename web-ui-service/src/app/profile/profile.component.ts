import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Profile } from '../model/profile';
import { ProfileService } from './profile.service';
import { SuspiciousService } from './suspicious.service';
import { Suspicious } from '../model/task';
import { WebSocketService } from "../websocket/websocket.service";
import { TaskChannelService } from "../websocket/task-channel.service";
import { AlertChannelService } from "../websocket/alert-channel.service";

import { AlertsService } from '../alerts/alerts.service';
import { Alert } from '../model/alert';

@Component({
  selector: 'profile',
  templateUrl: 'profile.html',
  styleUrls: ['../../assets/css/sb-admin-2.css'],
  providers: [ProfileService, SuspiciousService, WebSocketService, TaskChannelService, AlertsService, AlertChannelService]
})
export class ProfileComponent { 

	  profile: Profile;
    suspiciousList : [Suspicious];

    alertsList : [Alert];
    bellColor = '#337ab7';

  	constructor(private router: Router, 
                private profileService: ProfileService,
                private suspiciousService: SuspiciousService,
                private taskChannelService:TaskChannelService,
                private alertChannelService:AlertChannelService,
                private alertsService:AlertsService,
                private webSocketService:WebSocketService) { 

      this.webSocketService.start( 'ws://tvss.me:7000/recognition-service/ws-alerts' );
        this.alertChannelService.observableData.subscribe( ( data:Object ) => {
            this.addAlert(data);
        });
    }

    private addAlert(data: any) {
      let alert = <Alert>({
        id: data.id,
        type: data.type,
        message: data.message,
        time: data.date,
        refs: data.refs
      
      });

      // todo change it to use channels properly
      if(!data.type) {
        return;
      }

      this.bellColor = '#e81f1f';
      this.alertsList.unshift(alert);
      if(this.alertsList.length > 3) {
        this.alertsList.pop();
      }
    }

    setDefaultBellColor() {
      this.bellColor = '#337ab7';
    }

  	ngOnInit() { 
        console.log('Fetching profile of the authenticated user');
        this.profileService.getAuthenticated().subscribe(
                      response => {
                        console.log("Authenticated user's profile:");
                        console.log(response);
                        this.profile = response;
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

        console.log('Fetching suspicious found by user');
        this.suspiciousService.getSuspicious().subscribe(
                  response => {
                    console.log("Suspicious, found by user:");
                    console.log(response);
                    this.suspiciousList = response;
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

        console.log('Fetching user alerts');
        this.alertsService.getRecentAlerts().subscribe(
                  response => {
                    console.log("User alerts:");
                    console.log(response);
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

    logout() {
      localStorage.removeItem('token');
      this.router.navigateByUrl('/');
    }

}
