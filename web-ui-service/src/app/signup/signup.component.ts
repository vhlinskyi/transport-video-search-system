import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SignUpService } from './signup.service';

@Component({
  selector: 'sign-up',
  templateUrl: 'signup.html',
  providers: [SignUpService]
})
export class SignUpComponent { 

  private data: any;
  errorMessage: string;
  constructor(public router: Router ,private signUpService: SignUpService) {}

  registerUser(event:any, username:string, email:string, password:string, repeatPassword:string) {
    event.preventDefault();
    this.signUpService.registerUser(username, email, password, repeatPassword)
                     .subscribe(
                        response => {
                          this.router.navigateByUrl('/login');
                        },
                        error => {
                          alert(error);
                        }
                      );
 }
}
