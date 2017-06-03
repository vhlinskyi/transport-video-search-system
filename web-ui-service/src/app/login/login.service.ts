import { Injectable } from '@angular/core';
import { Http , URLSearchParams , Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';


@Injectable()
export class LoginService {
  private OauthLoginEndPointUrl = 'http://tvss.me:4000/auth-service/oauth/token';  // Oauth Login EndPointUrl to web API
  private clientId ='browser';
  private clientSecret ='';
  constructor(public http: Http) {}

  login(username:string, password:string) : Observable<any> {
    let params: URLSearchParams = new URLSearchParams();
     params.set('username', username );
     params.set('password', password );
     params.set('client_id', this.clientId );
     // params.set('client_secret', this.clientSecret );
     params.set('grant_type', 'password' );

    let headers = new Headers();
    headers.append('Authorization', 'Basic YnJvd3Nlcjo=');
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post(this.OauthLoginEndPointUrl , params.toString(),
                 {
                   headers: headers
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
    let errMsg = (error.message) ? error.message :
    error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

  public logout() {
     localStorage.removeItem('token');
  }
}
