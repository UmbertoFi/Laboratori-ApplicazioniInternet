import { Component } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, UserService} from './_services';
import {Observable, pipe, Subscription} from 'rxjs';
import {LineaDB} from './_models';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-router';
  // linee: LineaDB[];
  constructor(
    private userService: UserService) {}

  /* ngOnInit() {
    this.userService.getlinee().subscribe(val => this.linee=val);
  } */
}
