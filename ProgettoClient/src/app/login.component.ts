import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import {AlertService, AuthenticationService, UserService} from './_services';
import {WebSocketService} from './_services/websocket.service';
import {Notifica} from './_models/notifica';



@Component({templateUrl: 'login.component.html'})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  notifications: Notifica;
  // returnUrl: string;

  constructor(
    private webSocketService: WebSocketService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) {



// Open connection with server socket
    const stompClient = this.webSocketService.connect();
    stompClient.connect({}, frame => {

      // Subscribe to notification topic
      stompClient.subscribe('/topic/notification', notifications => {

        // Update notifications attribute with the recent messsage sent from the server
        this.notifications = JSON.parse(notifications.body);
      });
    });
  }

  ngOnInit() {
    if (localStorage.getItem('access_token') != null) {
      this.alertService.error('Utente già loggato! Premere Logout per effettuare altre operazioni!', true);
      this.router.navigate(['/simpleuser']);
    }
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    // reset login status
    // this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.authenticationService.login(this.loginForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data) {
            this.userService.login(data);
            this.router.navigate(['/simpleuser']);
          }
        },
        error => {
          this.alertService.error('Login fallito!');
        });
  }
}
