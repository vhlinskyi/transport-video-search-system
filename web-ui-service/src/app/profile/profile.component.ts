import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Profile } from '../model/profile';
import { ProfileService } from './profile.service';

@Component({
  selector: 'profile',
  templateUrl: 'profile.html',
  styleUrls: ['../../assets/css/sb-admin-2.css'],
  providers: [ProfileService]
})
export class ProfileComponent { 

	profile: Profile;

  games : [{
        game: string,
        platform : string,
        release : string
    }];

  	constructor(private router: Router, private profileService: ProfileService) { }

  	ngOnInit() { 
  	  console.log('Fetching profile of the authenticated user');
  	  this.profileService.getAuthenticated().subscribe(
                          response => {
                            console.log("Authenticated user's profile:");
                            console.log(response);
                            this.profile = response;
                          },
                          error => {
                          	if(error.status === 401) {
                          		localStorage.removeItem('token');
  								this.router.navigateByUrl('/login');
                          	} else {
  								alert(error.json());
                          	}
                          }
                        );


      this.games = [{
            game : "Deus Ex: Mankind Divided",
            platform: " Xbox One, PS4, PC",
            release : "August 23"
        },
        {
            game : "Hue",
            platform: " Xbox One, PS4, Vita, PC",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "Deus Ex: Mankind Divided",
            platform: " Xbox One, PS4, PC",
            release : "August 23"
        },
        {
            game : "Hue",
            platform: " Xbox One, PS4, Vita, PC",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "Deus Ex: Mankind Divided",
            platform: " Xbox One, PS4, PC",
            release : "August 23"
        },
        {
            game : "Hue",
            platform: " Xbox One, PS4, Vita, PC",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "Deus Ex: Mankind Divided",
            platform: " Xbox One, PS4, PC",
            release : "August 23"
        },
        {
            game : "Hue",
            platform: " Xbox One, PS4, Vita, PC",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "Deus Ex: Mankind Divided",
            platform: " Xbox One, PS4, PC",
            release : "August 23"
        },
        {
            game : "Hue",
            platform: " Xbox One, PS4, Vita, PC",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        },
        {
            game : "The Huntsman: Winter's Curse",
            platform: "PS4",
            release : "August 23"
        }];

  	}

  	ngOnDestroy() {
  	}

}
