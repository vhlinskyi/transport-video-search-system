import { Component, OnInit, OnDestroy } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
import { TaskUploader } from './task-uploader.class';
import { Observable } from 'rxjs/Rx';
import { Router } from '@angular/router';

import { WebSocketService } from "../websocket/websocket.service";
import { TaskChannelService } from "../websocket/task-channel.service";
import { AlertChannelService } from "../websocket/alert-channel.service";

import { Task } from "../model/task";

import { AlertsService } from '../alerts/alerts.service';
import { Alert } from '../model/alert';


@Component({
    selector: 'tasks',
    templateUrl: 'tasks.html',
    styleUrls: ['../../assets/css/sb-admin-2.css'],
    providers: [WebSocketService, TaskChannelService, AlertsService, AlertChannelService]
})
export class TasksComponent { 

    public uploader:TaskUploader;
    public hasBaseDropZoneOver:boolean = false;
    public hasAnotherDropZoneOver:boolean = false;

    private baseURL = 'http://tvss.me:4000/recognition-service/tasks/';
    private http: Http;
    private currentTaskId:string;

    // TODO change to the dictionary
    tasks : Task[] = [];
    
    alertsList : [Alert];
    bellColor = '#337ab7';
    

    public constructor(http: Http,
                      private router: Router,
                      private alertsService: AlertsService,
                      private taskChannelService:TaskChannelService,
                      private alertChannelService:AlertChannelService,  
                      private webSocketService:WebSocketService) {
        
        this.http = http;
        this.webSocketService.start( 'ws://tvss.me:7000/recognition-service/ws-tasks' );

        let self = this;
        this.uploader = new TaskUploader(this.baseURL, http, self);
        this.taskChannelService.observableData.subscribe( ( data:Object ) => {
            this.updateTask(data);
        } );

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


    private updateTask(task:any): void {
        
        for( let i = 0; i < this.tasks.length; i++) {
            if(this.tasks[i].id === task.id) {
                this.tasks[i].done = task.done;
                this.tasks[i].processing = task.processing;
                this.tasks[i].progress = (task.done) ? '100%' : ((task.processed / task.approximate_size) * 100) + '%';
                // console.log("Progress");
                // console.log(this.tasks[i].progress);
            }
        }
    }

    ngOnInit() { 

        let headers = new Headers();
        headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
        this.http.get(this.baseURL,
                     {
                       headers: headers
                     })
                      .map(this.handleData)
                      .map(this.toTasks)
                      .catch(this.handleError)
                      .subscribe(result => {
                              this.tasks = result;
                        });

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

    public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
    }

    public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
    }

    public createTask() {

        let headers = new Headers();
        headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
        this.http.post(this.baseURL,
                     { },
                     {
                       headers: headers
                     })
                      .map(this.handleData)
                      .catch(this.handleError)
                      .subscribe(result => {
                              let createdTask = result;
                              this.currentTaskId = createdTask.id;
                              this.uploader.uploadFilesToTask(createdTask.id);
                        });

    }

    public addTask(task:any): void {
        this.tasks.unshift(task);
    }

    private toTasks(tasksData: any) {
        
        let tasks = [];
        for(let i = 0; i < tasksData.length; i++) {
            tasks.push({
                id: tasksData[i].id,
                images_number: tasksData[i].images_number,
                videos_number: tasksData[i].videos_number,
                done: tasksData[i].done,
                processing: tasksData[i].processing,
                date: tasksData[i].date,
                approximate_size : tasksData[i].approximate_size,
                progress : (tasksData[i].done) ? '100%' : '0%'
            });
        }

        return tasks;
    }

    private handleData(res: Response) {
        let body = res.json();
        return body;
    }

    private handleError (error: any) {

        if(error.json() && error.json().status === 401) {
          localStorage.removeItem('token');
          this.router.navigateByUrl('/login');
        } else {
          this.showErrorMessage(error);
        }
        return Observable.throw(error);
    }

    private showErrorMessage(err: any) {
      let errorBody = err.json();
      alert(errorBody.message);
    }

  	ngOnDestroy() {
  	}

    logout() {
      localStorage.removeItem('token');
      this.router.navigateByUrl('/');
    }

}
