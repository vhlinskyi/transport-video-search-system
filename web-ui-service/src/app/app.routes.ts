// Imports
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LandingComponent } from './landing/landing.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signup/signup.component';
import { ProfileComponent } from './profile/profile.component';
import { TasksComponent } from './tasks/tasks.component';
import { TaskDetailsComponent }   from './task-details/task-details.component';
import { SettingsComponent } from './settings/settings.component';
import { AlertsComponent }   from './alerts/alerts.component';

import { CanActivateViaOAuthGuard } from './oAuth.canActivateGuard';

// Route Configuration
export const appRoutes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'profile', component: ProfileComponent, canActivate : [CanActivateViaOAuthGuard] },
  { path: 'tasks', component: TasksComponent, canActivate : [CanActivateViaOAuthGuard] },
  { path: 'tasks/:id', component: TaskDetailsComponent, canActivate : [CanActivateViaOAuthGuard] },
  { path: 'settings', component: SettingsComponent, canActivate : [CanActivateViaOAuthGuard] },
  { path: 'alerts', component: AlertsComponent, canActivate : [CanActivateViaOAuthGuard] }

];

// Export routes
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
