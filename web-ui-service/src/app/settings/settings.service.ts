import { Injectable } from '@angular/core';
import { Http , URLSearchParams , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';


@Injectable()
export class SettingsService {

  private accountServiceURL = 'http://tvss.me:4000/account-service/';

  constructor(public http: Http) {}

  updateProfile(first_name:string, last_name:string, skype_name: string, 
              phone: string, quote: string, file: any) : Observable<any> {

    let formData = new FormData();
    formData.append('firstName', first_name);
    formData.append('lastName', last_name);
    formData.append('skypeName', skype_name);
    formData.append('phone', phone);
    formData.append('quote', quote);

    if(file && file.files[0]) {
      formData.append('file', file.files[0], file.files[0].name);
    }

    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.post(this.accountServiceURL + '/current/update' , formData,
                 {
                   headers: headers
                 })
                .map(this.handleData)
               .catch(this.handleError);
  }

  private handleData(res: Response) {
    let body = res.json();
    return body;
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
