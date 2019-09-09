import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import {AlertService, AuthenticationService, UserService} from './_services';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {WebSocketService} from './_services/websocket.service';
import {Notifica} from './_models/notifica';



@Component({templateUrl: 'login.component.html'})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  username_blur = false;
  password_blur = false;
  // returnUrl: string;

  newEmailForm: FormGroup;
  codiceForm: FormGroup;
  newPasswordForm: FormGroup;
  email_blur = false;
  codice_blur = false;
  confirmPassword_blur = false;
  newPassword_blur = false;
  messaggio: string;
  submitted1 = false;
  submitted2 = false;
  submitted3 = false;

  step1 = true;
  step2 = false;
  step3 = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private modalService: NgbModal) {
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

    this.newEmailForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]]
    });

    this.codiceForm = this.formBuilder.group({
      codice: ['', [Validators.required]]
    });

    this.newPasswordForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(12)]],
      confirmPassword: ['',  [Validators.required, Validators.minLength(6), Validators.maxLength(12)]]
    }, {
      validators: [MustMatch('password', 'confirmPassword')]
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
            this.alertService.success('Login effettuato, benvenuto!', true);
            this.router.navigate(['/simpleuser']);
          }
        },
        error => {
          this.alertService.error('Login fallito!');
        });
  }

  onBlur(field: number) {
    if (field == 0) {
      this.username_blur = true;
    } else if (field == 1) {
      this.password_blur = true;
    }
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }



  onBlurNewPassword(field: number) {
    if (field == 0) {
      this.email_blur = true;
    } else if (field == 1) {
      this.codice_blur = true;
    } else if (field == 2) {
      this.newPassword_blur = true;
    } else if (field == 3) {
      this.confirmPassword_blur = true;
    }

  }

  codiceSubmit() {

    this.submitted2 = true;

    // stop here if form is invalid
    if (this.codiceForm.invalid) {
      return;
    }

    this.userService.recoverCheck(this.codiceForm.value, this.newEmailForm.value)
      .pipe(first())
      .subscribe(
        data => {
          // this.userService.login(data);
          console.log('Ora puoi inserire le credenziali');
          this.messaggio = 'Ora puoi inserire le credenziali';
          this.step2 = false;
          this.step3 = true;
          console.log('step1 ' + this.step1 + ' step2 ' + this.step2);
        },
        error => {
          this.messaggio = 'Codice scaduto o errato!';
        });

  }



  emailSubmit() {

    this.submitted1 = true;

    // stop here if form is invalid
    if (this.newEmailForm.invalid) {
      return;
    }

    this.userService.recover(this.newEmailForm.value)
          .pipe(first())
          .subscribe(
            data => {
                // this.userService.login(data);
                console.log('Controlla la tua email!');
                this.messaggio = 'Controlla la tua email!';
                this.step1 = false;
                this.step2 = true;
                console.log('step1' + this.step1 + 'step2' + this.step2);
            },
            error => {
              this.messaggio = 'Email non valida.Riprova!';
            });

      }

  newPasswordSubmit() {
    this.submitted3 = true;

    // stop here if form is invalid
    if (this.newEmailForm.invalid) {
      return;
    }

    this.userService.recoverChange(this.newEmailForm.value, this.newPasswordForm.value)
      .pipe(first())
      .subscribe(
        data => {
          // this.userService.login(data);
          console.log('password cambiata');
          this.messaggio = 'Password cambiata!';
          this.modalService.dismissAll();
        },
        error => {
          this.messaggio = 'errore nell inserimento password';
        });
  }

}
function MustMatch(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
    const matchingControl = formGroup.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      // return if another validator has already found an error on the matchingControl
      return;
    }

    // set error on matchingControl if validation fails
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({mustMatch: true});
    } else {
      matchingControl.setErrors(null);
    }
  };
}
