import {Component} from '@angular/core';

@Component({
  selector: 'landing',
  templateUrl: 'landing.html',
  styleUrls: ['landing.css']
})
export class LandingComponent {

	isAuthenticated() {
		return localStorage.getItem('token') && localStorage.getItem('token') != null;
	}
}
