import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule }    from '@angular/http';

import { AppComponent }  from './app.component';

import { LandingComponent }   from './landing/landing.component';
import { LoginComponent }   from './login/login.component';
import { SignUpComponent }   from './signup/signup.component';
import { ProfileComponent }   from './profile/profile.component';

import { CanActivateViaOAuthGuard } from './oAuth.canActivateGuard';

// Import configured routes
import { routing } from './app.routes';

@NgModule({
  providers:    [ CanActivateViaOAuthGuard ],
  imports:      [ BrowserModule, routing, HttpModule ],
  declarations: [ 	AppComponent, 
  					LoginComponent, 
  					LandingComponent,
  					SignUpComponent,
  					ProfileComponent  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
