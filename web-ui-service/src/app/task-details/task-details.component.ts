import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Http , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { WebSocketService } from "../websocket/websocket.service";
import { TaskChannelService } from "../websocket/task-channel.service";

import { Task } from "../model/task";


@Component({
    selector: 'task-details',
    templateUrl: 'task-details.html',
    styleUrls: ['../../assets/css/sb-admin-2.css'],
    providers: [WebSocketService, TaskChannelService]
})
export class TaskDetailsComponent {

    public recognitionServiceURL = 'http://tvss.me:4000/recognition-service/';
    task : Task;

    public constructor(private http: Http, 
                       private taskChannelService:TaskChannelService, 
                       private webSocketService:WebSocketService,
                       public route: ActivatedRoute) {
        
        this.webSocketService.start( 'ws://tvss.me:7000/recognition-service/ws-tasks' );
        this.taskChannelService.observableData.subscribe( ( data:Object ) => {
            this.updateTask(data);
        });
    }

    ngOnInit() { 
        this.route.params
                .subscribe(params => {
                    this.fetchTaskDetails(params['id']);
                });
    }

    private fetchTaskDetails(taskId:string):void {
        
        let headers = new Headers();
        headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
        this.http.get(this.recognitionServiceURL + '/tasks/' + taskId,
                     {
                       headers: headers
                     })
                      .map(this.handleData)
                      .map(this.toTask)
                      .catch(this.handleError)
                      .subscribe(result => {
                          console.log("Task fetched:");
                          console.log(result);
                              this.task = result;
                        });
    }

    private updateTask(task:any): void {

      if(!this.task) {
        return;
      }

      this.task.done = task.done;
      this.task.processing = task.processing;
      this.task.recognized = task.recognized;
      
      // todo review, change it
      if(!this.task.suspicious){
        this.task.suspicious = task.suspicious;
      } else if(task.suspicious){

        for(let i = 0; i < task.suspicious.length; i++) {
          let receivedSuspicious = task.suspicious[i];

          let exists = false;
          for(let j = 0; j < this.task.suspicious.length; j++) {
            let existingSuspicious = this.task.suspicious[j];
            if(existingSuspicious.wanted_transport.number_plate === receivedSuspicious.wanted_transport.number_plate) {
              exists = true;
              break;
            }
          }

          if(!exists) {
            this.task.suspicious.push(receivedSuspicious);
          }

        }

      }

      let progress = (task.done) ? 100 : ((task.processed / task.approximate_size) * 100);
      this.task.progress = (Math.round(progress * 100) / 100) + '%';
    }

    private toTask(taskData: any) {
        
        let task = {
                id: taskData.id,
                images_number: taskData.images_number,
                videos_number: taskData.videos_number,
                done: taskData.done,
                processing: taskData.processing,
                date: taskData.date,
                recognized: taskData.recognized,
                suspicious: taskData.suspicious,
                approximate_size : taskData.approximate_size,
                progress : (taskData.done) ? '100%' : '0%'
            };

        return task;
    }

    private handleData(res: Response) {
        let body = res.json();
        return body;
    }

    private handleError (error: any) {
        return Observable.throw(error);
    }
}
