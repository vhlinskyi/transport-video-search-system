import { Injectable } from '@angular/core';
import { Http , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Profile } from '../model/profile';


@Injectable()
export class ProfileService {
  
  private accountServiceUrl = 'http://tvss.me:4000/account-service/';

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
      email:  res.email,
      first_name: res.first_name,
      last_name: res.last_name,
      skype_name: res.skype_name,
      phone: res.phone,
      quote: res.quote,
      picture: res.picture
    });

    return profile;
  }

  private handleError (error: any) {
    return Observable.throw(error);
  }


}
