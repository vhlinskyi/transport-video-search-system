import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Profile } from '../model/profile';
import { ProfileService } from '../profile/profile.service';
import { SettingsService } from './settings.service';
import { WebSocketService } from "../websocket/websocket.service";
import { AlertChannelService } from "../websocket/alert-channel.service";

import { AlertsService } from '../alerts/alerts.service';
import { Alert } from '../model/alert';

@Component({
  selector: 'settings',
  templateUrl: 'settings.html',
  styleUrls: ['../../assets/css/sb-admin-2.css'],
  providers: [ProfileService, SettingsService, WebSocketService, AlertsService, AlertChannelService]
})
export class SettingsComponent { 

    profile: Profile;

    alertsList : [Alert];
    bellColor = '#337ab7';

  	constructor(private router: Router, 
                private profileService: ProfileService, 
                private settingsService: SettingsService,
                private alertChannelService:AlertChannelService,
                private alertsService:AlertsService,
                private webSocketService:WebSocketService) { 

      this.webSocketService.start( 'ws://tvss.me:7000/recognition-service/ws-alerts' );
        this.alertChannelService.observableData.subscribe( ( data:Object ) => {
            this.addAlert(data);
        });
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
  								            this.showErrorMessage(error);
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

    private addAlert(data: any) {
      let alert = <Alert>({
        id: data.id,
        type: data.type,
        message: data.message,
        time: data.date,
        refs: data.refs
      
      });

      // todo change it to use channels properly
      // if(!data.type) {
      //   return;
      // }

      this.bellColor = '#e81f1f';
      this.alertsList.unshift(alert);
      if(this.alertsList.length > 3) {
        this.alertsList.pop();
      }
    }

    setDefaultBellColor() {
      this.bellColor = '#337ab7';
    }


    updateProfile(event:any, first_name:string, last_name:string, skype_name:string, phone:string, quote:string, file:any) {
    
    event.preventDefault();
    this.settingsService.updateProfile(first_name, last_name, skype_name, phone, quote, file)
                     .subscribe(
                        response => {
                          window.location.reload();
                        },
                        error => {
                          alert(error);
                        }
                      );
   }

  	ngOnDestroy() {
  	}

    private showErrorMessage(err: any) {
      let errorBody = err.json();
      alert(errorBody.message);
    }

    logout() {
      localStorage.removeItem('token');
      this.router.navigateByUrl('/');
    }

}
