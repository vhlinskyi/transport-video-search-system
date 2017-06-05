import { Injectable } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Suspicious, WantedTransport } from '../model/task';


@Injectable()
export class SuspiciousService {
  
  private accountServiceUrl = 'http://tvss.me:4000/recognition-service/';

  constructor(public http: Http) {}

  getSuspicious() : Observable<[Suspicious]> {

    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get(`${this.accountServiceUrl}/suspicious`,
                 {
                   headers: headers
                 })
                  .map(this.handleResponse)
                  .map(this.toSuspiciousList)
                  .catch(this.handleError);
  }

  private handleResponse(res: Response) {
    let body = res.json();
    return body;
  }

  private toSuspiciousList(res: any) {
    
    let suspiciousList = [];

    for(let i = 0; i < res.length; i++) {

      let wantedTransport = <WantedTransport>({
        body_number: res[i].wanted_transport.body_number,
        chassis_number: res[i].wanted_transport.chassis_number,
        color: res[i].wanted_transport.color,
        department: res[i].wanted_transport.department,
        model: res[i].wanted_transport.model,
        number_plate: res[i].wanted_transport.number_plate
      
      });

      let suspicious = <Suspicious>({
        image: res[i].image,
        wanted_transport: wantedTransport
      
      });

      suspiciousList.push(suspicious);

    }

    return suspiciousList;
  }

  private handleError (error: any) {
    return Observable.throw(error);
  }


}
