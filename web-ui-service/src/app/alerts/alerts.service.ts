import { Injectable } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Alert } from '../model/alert';


@Injectable()
export class AlertsService {
  
  private accountServiceUrl = 'http://tvss.me:4000/recognition-service/';

  constructor(public http: Http) {}

  getAlerts() : Observable<[Alert]> {

    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get(`${this.accountServiceUrl}/alerts`,
                 {
                   headers: headers
                 })
                  .map(this.handleResponse)
                  .map(this.toAlertsList)
                  .catch(this.handleError);
  }

  getRecentAlerts() : Observable<[Alert]> {

    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get(`${this.accountServiceUrl}/recent-alerts`,
                 {
                   headers: headers
                 })
                  .map(this.handleResponse)
                  .map(this.toAlertsList)
                  .catch(this.handleError);
  }

  private handleResponse(res: Response) {
    console.log(res);
    let body = res.json();
    return body;
  }

  private toAlertsList(res: any) {
    
    let alertList = [];

    for(let i = 0; i < res.length; i++) {

      let alert = <Alert>({
        id: res[i].id,
        type: res[i].type,
        message: res[i].message,
        time: res[i].date,
        refs: res[i].refs
      
      });

      alertList.push(alert);

    }

    return alertList;
  }

  private handleError (error: any) {
        console.log(error);
    return Observable.throw(error);
  }


}
