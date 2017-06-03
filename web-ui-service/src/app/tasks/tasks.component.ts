import { Component, OnInit, OnDestroy } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
import { TaskUploader } from './task-uploader.class';
import { Observable } from 'rxjs/Rx';

import { WebSocketService } from "../websocket/websocket.service";
import { TaskChannelService } from "../websocket/task-channel.service";

import { Task } from "../model/task";


@Component({
    selector: 'tasks',
    templateUrl: 'tasks.html',
    styleUrls: ['../../assets/css/sb-admin-2.css'],
    providers: [WebSocketService, TaskChannelService]
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

    public constructor(http: Http, private taskChannelService:TaskChannelService, private webSocketService:WebSocketService) {
        
        this.http = http;
        this.webSocketService.start( 'ws://tvss.me:7000/recognition-service/ws-tasks' );

        let self = this;
        this.uploader = new TaskUploader(this.baseURL, http, self);
        this.taskChannelService.observableData.subscribe( ( data:Object ) => {
            this.updateTask(data);
        } );

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
        return Observable.throw(error);
    }

  	ngOnDestroy() {
  	}

}
