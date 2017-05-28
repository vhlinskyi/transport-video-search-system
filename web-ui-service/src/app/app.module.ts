import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule }    from '@angular/http';

import { AppComponent }  from './app.component';

import { LandingComponent }   from './landing/landing.component';
import { LoginComponent }   from './login/login.component';
import { SignUpComponent }   from './signup/signup.component';
import { ProfileComponent }   from './profile/profile.component';
import { TasksComponent }   from './tasks/tasks.component';

import { CanActivateViaOAuthGuard } from './oAuth.canActivateGuard';

// Import configured routes
import { routing } from './app.routes';

// Importing ng2-pagination
import {Ng2PaginationModule} from 'ng2-pagination';

// Importing ng2-pagination
import {FileUploadModule} from 'ng2-file-upload';

@NgModule({
  providers:    [ CanActivateViaOAuthGuard ],
  imports:      [ BrowserModule, routing, HttpModule, Ng2PaginationModule, FileUploadModule ],
  declarations: [ 	AppComponent, 
  					LoginComponent, 
  					LandingComponent,
  					SignUpComponent,
  					ProfileComponent,
  					TasksComponent
  				],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
