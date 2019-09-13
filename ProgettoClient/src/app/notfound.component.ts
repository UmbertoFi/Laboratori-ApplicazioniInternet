import {Component, OnInit} from '@angular/core';
import {WebSocketService} from './_services/websocket.service';
import {AlertService, AuthenticationService, LineaService, UserService} from './_services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-notfound',
  templateUrl: './notfound.component.html',
  styleUrls: ['./notfound.component.css']
})
export class NotFoundComponent {

  constructor(private webSocketService: WebSocketService, private authenticationService: AuthenticationService, private alertService: AlertService, private router: Router) {}

    Home($event: MouseEvent) {
      this.router.navigate(['/']);
  }
}
