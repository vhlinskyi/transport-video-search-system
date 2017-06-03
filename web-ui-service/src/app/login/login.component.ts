import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { LoginService } from './login.service';

@Component({
  selector: 'my-login',
  templateUrl: 'login.html',
  providers: [LoginService]
})
export class LoginComponent {
  private data: any;
  errorMessage: string;
  constructor(public router: Router ,private loginService: LoginService) {}

  login(event:any, username:string, password:string) {
    event.preventDefault();
    this.loginService.login(username, password)
                     .subscribe(
                        response => {
                          localStorage.setItem('token', response.access_token);
                          this.router.navigateByUrl('/profile');
                        },
                        error => {
                          alert(error);
                        }
                      );
 }
}
