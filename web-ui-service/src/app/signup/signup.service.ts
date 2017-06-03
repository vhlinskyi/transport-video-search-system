import { Injectable } from '@angular/core';
import { Http , Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';


@Injectable()
export class SignUpService {
  // TODO use some configuration file
  private accountServiceUrl = 'http://tvss.me:4000/account-service/';
  constructor(public http: Http) {}

  registerUser(username:string, email:string, password:string, repeatPassword:string) : Observable<any> {
    
    return this.http.post(this.accountServiceUrl,
                 {
                   name: username,
                   email: email,
                   password: password,
                   matchingPassword: repeatPassword
                 }).map(this.handleData)
               .catch(this.handleError);
  }

  private handleData(res: Response) {
    let body = res.json();
    return body;
  }

  private handleError (error: any) {

    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.json().message) ? error.json().message :
    error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }
}
