import { Component } from '@angular/core';
import {AlertService, AuthenticationService, UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-router';
  constructor() {}
}
