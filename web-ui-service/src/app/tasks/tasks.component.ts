import { Component, OnInit, OnDestroy } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
// import { Router } from '@angular/router';

import { FileUploader } from 'ng2-file-upload';

// const URL = '/api/';
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';


@Component({
  selector: 'profile',
  templateUrl: 'app/tasks/tasks.html',
  styleUrls: ['assets/css/sb-admin-2.css']  
})
export class TasksComponent { 

    private recognitionServiceUrl = 'http://192.168.1.146/recognition-service';

    games : [{
        game: string,
        platform : string,
        release : string
    }];

    public uploader:FileUploader = new FileUploader({url: URL});
    public hasBaseDropZoneOver:boolean = false;
    public hasAnotherDropZoneOver:boolean = false;

    public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
    }

    public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
    
    }


    // createNewTask(event:any) {

    //     event.preventDefault();


    //     let fileList: FileList = event.target.files;
    //     if(fileList.length > 0) {
            
    //         let file: File = fileList[0];
    //         let formData:FormData = new FormData();
    //         formData.append('uploadFile', file, file.name);
            
    //         let headers = new Headers();
    //         headers.append('Accept', 'application/json');
    //         headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));

    //         let options = new RequestOptions({ headers: headers });
    //         this.http.post(`${this.recognitionServiceUrl}/video/`, formData, options)
    //             .map(res => res.json())
    //             .catch(error => Observable.throw(error))
    //             .subscribe(
    //                 data => console.log('success'),
    //                 error => console.log(error)
    //             )
    //     }
    // }


  	// constructor(private router: Router, private profileService: ProfileService) { }

  	ngOnInit() { 

      this.games = [{
            game : "Deus Ex: Mankind Divided",
            platform: " Xbox One, PS4, PC",
            release : "August 23"
        },
        {
            game : "Hue",
            platform: " Xbox One, PS4, Vita, PC",
            release : "August 23"
        }];

  	}

  	ngOnDestroy() {
  	}

}
