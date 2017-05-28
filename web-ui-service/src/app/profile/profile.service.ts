import { Injectable } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Profile } from '../model/profile';


@Injectable()
export class ProfileService {
  
  private accountServiceUrl = 'http://192.168.1.146/account-service/';

  constructor(public http: Http) {}

  getAuthenticated() : Observable<Profile> {

    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get(`${this.accountServiceUrl}/current`,
                 {
                   headers: headers
                 })
                  .map(this.handleResponse)
                  .map(this.toProfile)
                  .catch(this.handleError);
  }

  private handleResponse(res: Response) {
    let body = res.json();
    return body;
  }

  private toProfile(res: any) {
    
    let profile = <Profile>({
      id:     res.id,
      name:   res.name,
      email:  res.email
    });

    return profile;
  }

  private handleError (error: any) {
    return Observable.throw(error);
  }


}
