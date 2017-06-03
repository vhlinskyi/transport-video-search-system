import { FileUploader } from 'ng2-file-upload';
import { Http , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { TasksComponent } from './tasks.component';

export class TaskUploader extends FileUploader {
	
	public constructor(baseURL: string, http : Http, taskConponent: TasksComponent) {
		super({
			url: baseURL,
			headers: [{ name: 'Authorization', value: 'Bearer ' + localStorage.getItem('token') }]
		});

    	this.baseURL = baseURL;
    	this.http = http;
    	this.taskConponent = taskConponent;
	}

	private baseURL:string;
    private http:Http;
    private taskConponent:TasksComponent;

    private currentTaskId:string;

    public uploadAll():void {

    	let headers = new Headers();
	    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
	    this.http.post(this.baseURL,
	    			 {
	    			 	key: 'value'
	    			 },
	                 {
	                   headers: headers
	                 })
	                  .map(this.handleData)
	                  .catch(this.handleError)
	                  .subscribe(result => {
	                  		this.currentTaskId = result.id;
					      	this.uploadFilesToTask(this.currentTaskId);
					    });

    }

    public uploadFilesToTask(taskId:string): void {

	    this.currentTaskId = taskId;
		super.setOptions({url: this.baseURL + taskId});
        super.uploadAll();
    }

    private preocessTask(taskId:string) : Observable<any> {

    	let headers = new Headers();
	    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));

	    return this.http.post(this.baseURL + taskId + '/process', {},
	                 {
	                   headers: headers
	                 });
    }

    private handleData(res: Response) {
	    let body = res.json();
	    return body;
	}

	public onCompleteAll(): any {
		this.preocessTask(this.currentTaskId)
						.map(this.handleData)
	                  	.catch(this.handleError)
	                 	.subscribe(result => {

	                 		this.taskConponent.addTask(result);
					      	setTimeout(() => { 
					      		super.clearQueue();
					      	}, 3000);
					    });
	}

  private handleError (error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
    error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

}