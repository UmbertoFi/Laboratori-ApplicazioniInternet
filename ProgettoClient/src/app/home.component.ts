import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AlertService} from './_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  constructor(private alertService: AlertService, private router: Router){}

  ngOnInit() {
    if (localStorage.getItem('access_token') != null) {
      this.alertService.error('Utente già loggato! Premere Logout per effettuare altre operazioni!', true);
      this.router.navigate(['/simpleuser']);
    }
  }


  Login($event: MouseEvent) {
    this.router.navigate(['/login']);
  }

  Registrazione($event: MouseEvent) {
    this.router.navigate(['/register']);
  }
}
